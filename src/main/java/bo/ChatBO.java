package bo;

import dto.MensajeDTO;
import dto.Tipo;
import mappers.MensajeMapper;
import red.ConexionSocket;

/**
 * Componente de lógica de negocio (Business Object) encargado de validar,
 * filtrar y canalizar el envío de mensajes en la sala de chat.
 */
public class ChatBO {
    
    /**
     * Valida la longitud y contenido del mensaje, aplica el filtro de censura
     * y gestiona su envío estructurado a través de la conexión por sockets.
     * * @param mensaje Objeto con la información y emisor del mensaje.
     * @param usuarioDestino Nombre del destinatario (o "ALL" para públicos).
     * @throws IllegalArgumentException Si el mensaje está vacío, excede los 250 caracteres o carece de destino privado.
     */
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
