package red;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Dario
 */
public class ConexionSocket {
    private static ConexionSocket instancia;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String usuarioActual;

    private ConexionSocket() {}

    public static ConexionSocket getInstancia() {
        if (instancia == null) {
            instancia = new ConexionSocket();
        }
        return instancia;
    }

    public boolean conectar(String host, int puerto, String usuario) {
        try {
            this.socket = new Socket(host, puerto);
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.usuarioActual = usuario;

            String jsonRegistro = "{\"type\":\"register\",\"from\":\"" + usuario + "\",\"to\":\"ALL\",\"text\":\"\"}";
            salida.println(jsonRegistro);

            String respuesta = entrada.readLine();
            if (respuesta != null && respuesta.contains("\"text\": \"OK\"")) {
                return true;
            } else {
                cerrarConexion();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enviarMensaje(String json) {
        if (salida != null) {
            salida.println(json);
        }
    }

    
    public String recibirMensaje() throws Exception {
        if (entrada != null) {
            return entrada.readLine();
        }
        return null;
    }

    public String getUsuarioActual() { return usuarioActual; }

    public void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
