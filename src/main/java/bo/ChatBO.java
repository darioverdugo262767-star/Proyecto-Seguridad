package bo;

import dto.MensajeDTO;
import dto.Tipo;
import mappers.MensajeMapper;
import red.ConexionSocket;

/**
 *
 * @author Dario
 */
public class ChatBO {
    
    public void procesarYEnviarMensaje(MensajeDTO mensaje, String usuarioDestino) throws IllegalArgumentException {
        
        if (mensaje.getContenido() == null || mensaje.getContenido().trim().isEmpty()) {
            throw new IllegalArgumentException("No puedes enviar un mensaje vacío.");
        }
        if (mensaje.getContenido().length() > 250) {
            throw new IllegalArgumentException("El mensaje supera el limite permitido de 250 caracteres.");
        }
        
        FiltroPalabras filtro = FiltroPalabras.getInstancia();
        String textoModificado = filtro.censurarTexto(mensaje.getContenido());
        
        mensaje.setContenido(textoModificado);

        String destinoRed = "ALL"; 
        if (mensaje.getTipo() == Tipo.PRIVADO) {
            if (usuarioDestino == null || usuarioDestino.trim().isEmpty()) {
                throw new IllegalArgumentException("Para mensajes privados debes especificar un destinatario.");
            }
            destinoRed = usuarioDestino;
        }

        String jsonListo = MensajeMapper.toPythonJson(mensaje, destinoRed);
        ConexionSocket.getInstancia().enviarMensaje(jsonListo);
    }
}
