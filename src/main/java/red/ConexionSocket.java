package red;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Gestiona la conexión segura SSL/TLS mediante sockets siguiendo el patrón Singleton.
 */
public class ConexionSocket {
    private static ConexionSocket instancia;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private String usuarioActual;

    private ConexionSocket() {}

    /**
     * Obtiene la única instancia de la conexión.
     */
    public static ConexionSocket getInstancia() {
        if (instancia == null) {
            instancia = new ConexionSocket();
        }
        return instancia;
    }

    /**
     * Establece la conexión cifrada con el servidor y registra al usuario.
     */
    public boolean conectar(String host, int puerto, String usuario) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager(){
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();
            
            this.socket = factory.createSocket(host, puerto);
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

    /**
     * Envía una cadena de texto estructurada en formato JSON al servidor.
     */
    public void enviarMensaje(String json) {
        if (salida != null) {
            salida.println(json);
        }
    }

    /**
     * Bloquea el hilo actual esperando recibir una línea de texto del servidor.
     */
    public String recibirMensaje() throws Exception {
        if (entrada != null) {
            return entrada.readLine();
        }
        return null;
    }

    public String getUsuarioActual() { 
        return usuarioActual; 
    }

    /**
     * Finaliza los flujos de E/S y destruye el socket de comunicación.
     */
    public void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null) socket.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}
