# server.py
import threading 
import socket
import argparse
from common import parse_msg, make_msg, MAX_CLIENTS, timestamp

class ChatServer:
    def __init__(self, host='0.0.0.0', port=9009):
        self.host = host
        self.port = port
        
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        
        self.users = {} 
        self.lock = threading.Lock()

    def start(self):
        """Arranca el servidor TCP."""
        self.sock.bind((self.host, self.port))
        self.sock.listen(MAX_CLIENTS)
        print(f"[SERVIDOR] Escuchando en TCP {self.host}:{self.port}")
        
        try:
            while True:
                conn, addr = self.sock.accept()
                threading.Thread(target=self.handle_client, args=(conn, addr), daemon=True).start()
        except KeyboardInterrupt:
            print("\nApagando servidor...")
        finally:
            self.sock.close()

    def handle_client(self, conn, addr):
        """Ciclo de vida de un cliente conectado."""
        print(f"[SERVIDOR] Nueva conexión desde {addr}")
        username = None
        try:
            raw = self.recv_msg(conn)
            msg = parse_msg(raw)
            
            if not msg or msg.get('type') != 'register':
                conn.close()
                return
            
            username = msg.get('from')
            
            with self.lock:
                if username != "SERVER_GUI" and len([u for u in self.users if u != "SERVER_GUI"]) >= MAX_CLIENTS:
                    self.send_msg(conn, make_msg('register_ack', text='FULL'))
                    conn.close()
                    return
                if username != "SERVER_GUI" and (username in self.users or not username):
                    self.send_msg(conn, make_msg('register_ack', text='NAME_TAKEN'))
                    conn.close()
                    return
                
                self.users[username] = conn
            
            self.send_msg(conn, make_msg('register_ack', sender='server', to=username, text='OK'))
            
            if username != "SERVER_GUI":
                self.broadcast(
                    make_msg('message', username, 'ALL', f"{username} se ha unido al chat"),
                    exclude=username
                )
        
            while True:
                raw = self.recv_msg(conn)
                if not raw: 
                    break 
                
                m = parse_msg(raw)
                if not m: 
                    continue
                
                self.process_message(m, username)
                
        except Exception as e:
            print(f"[ERROR] Con {username if username else addr}: {e}")
        finally:
            if username:
                with self.lock:
                    if username in self.users:
                        del self.users[username]
                self.broadcast(make_msg('message', 'server', 'ALL', f"{username} se ha desconectado"))
            conn.close()
            print(f"[SERVIDOR] Conexión cerrada con {username if username else addr}")


    def process_message(self, m, sender):
        to = m.get('to', 'ALL')
        text = m.get('text', '')

        print(f"[CMD TRAFICO] [{sender}] -> [{to}]: {text}")

        if sender == "SERVER_GUI":
            if text == "/shutdown":
                print("\n[!] APAGANDO EL SERVIDOR POR ORDEN DEL ADMINISTRADOR...")
                self.broadcast(make_msg('message', 'server', 'ALL', "El servidor ha sido cerrado por el Administrador."))
                with self.lock:
                    for c in list(self.users.values()):
                        try: c.close()
                        except: pass
                import os
                os._exit(0)

            elif text.startswith("/kick "):
                target_user = text.split(" ")[1]
                print(f"\n[!] Intentando expulsar a: {target_user}")
                with self.lock:
                    if target_user in self.users:
                        client_socket = self.users[target_user]
                        try:
                            self.send_msg(client_socket, make_msg('message', 'server', target_user, "Has sido desconectado."))
                            client_socket.close()
                        except: pass
                        del self.users[target_user]
                self.broadcast(make_msg('message', 'server', 'ALL', f"{target_user} se ha desconectado"))
                return
            return  

        with self.lock:
            msg_a_enviar = make_msg('message', sender, to, text)
            for user, conn in self.users.items():
                if user != "SERVER_GUI":
                    try:
                        if to == 'ALL' or user == to or user == sender:
                            if sender != "SERVER_GUI":
                                self.send_msg(conn, msg_a_enviar)
                    except:
                        pass
                        
            if "SERVER_GUI" in self.users:
                try:
                    self.send_msg(self.users["SERVER_GUI"], msg_a_enviar)
                except:
                    pass

    def broadcast(self, data_bytes, exclude=None):
        """Envía un mensaje a todos los usuarios conectados."""
        with self.lock:
            for user, conn in list(self.users.items()):
                if user == exclude: 
                    continue
                try:
                    self.send_msg(conn, data_bytes)
                except Exception: 
                    pass

    def send_private(self, to_user, data_bytes, sender):
        """Envía un mensaje privado a un usuario específico."""
        with self.lock:
            conn = self.users.get(to_user)

        if conn is None:
            self.send_error(sender, f"El usuario '{to_user}' no está conectado.")
            return

        try:
            self.send_msg(conn, data_bytes)
        except Exception:
            pass
        
    def send_error(self, destination, error_text):
        """Envía un mensaje de error directo a un cliente."""
        with self.lock:
            conn = self.users.get(destination)
        if conn:
            try:
                self.send_msg(conn, make_msg('error', 'server', destination, error_text))
            except Exception:
                pass

    def send_msg(self, conn, data: bytes):
        """"Asegura que el mensaje sea bytes y termine exactamente con un \n."""
        if isinstance(data, str):
            payload = data.encode('utf-8')
        else:
            payload = data
            
        if not payload.endswith(b'\n'):
            payload += b'\n'
        conn.sendall(payload)

    def recv_msg(self, conn):
        """Lee del socket de forma segura garantizando retornar bytes limpios."""
        data = b''
        while True:
            try:
                part = conn.read(1024) if hasattr(conn, 'read') else conn.recv(1024)
                if not part: 
                    return b''
                data += part
                if b'\n' in data: 
                    break
            except Exception:
                return b''
        return data 

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-host', default='0.0.0.0') 
    parser.add_argument('-port', type=int, default=9009)
    args = parser.parse_args()
    
    s = ChatServer(host=args.host, port=args.port)
    s.start()

    