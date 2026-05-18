package mappers;

import dto.MensajeDTO;
import dto.Tipo;
import dto.UsuarioDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Utilidad de mapeo para convertir objetos MensajeDTO a cadenas estructuradas JSON y viceversa.
 */
public class MensajeMapper {
    private static final Logger logger = Logger.getLogger(MensajeMapper.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Serializa un objeto MensajeDTO a una cadena en formato JSON apta para el servidor Python.
     */
    public static String toPythonJson(MensajeDTO mensaje, String destinatario) {
        String nombreEmisor = (mensaje.getEmisor() != null) ? mensaje.getEmisor().getNombre() : "Desconocido";
        
        String fechaFormateada = "";
        if (mensaje.getFecha() != null) {
            fechaFormateada = mensaje.getFecha().format(FORMATTER);
        }

        String contenidoLimpio = mensaje.getContenido()
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "");

        return "{"
            + "\"type\":\"message\","
            + "\"from\":\"" + nombreEmisor + "\","
            + "\"to\":\"" + destinatario + "\"," 
            + "\"text\":\"" + contenidoLimpio + "\","
            + "\"timestamp\":\"" + fechaFormateada + "\""
            + "}";
    }

    /**
     * Deserializa una cadena JSON para reconstruir y retornar un objeto MensajeDTO.
     */
    public static MensajeDTO toMensajeDTO(String json) {
        try {
            String text = extraerValorJson(json, "text");
            String from = extraerValorJson(json, "from");
            String to = extraerValorJson(json, "to");
            String timestampStr = extraerValorJson(json, "timestamp");
            
            LocalDateTime fechaReal;
            if (timestampStr != null && !timestampStr.isEmpty()) {
                fechaReal = LocalDateTime.parse(timestampStr, FORMATTER);
            } else {
                fechaReal = LocalDateTime.now();
            }
            
            UsuarioDTO emisor = new UsuarioDTO(0L, from, "", null); 
            Tipo tipo = "ALL".equalsIgnoreCase(to) ? Tipo.PUBLICO : Tipo.PRIVADO; 
            
            return new MensajeDTO(text, emisor, fechaReal, tipo);
        } catch (Exception e) {
            logger.severe("Error al mapear el mensaje JSON: " + e.getMessage());
            return null;
        }
    }

    /**
     * Extrae mediante subcadenas el valor plano asociado a una clave dentro del JSON.
     */
    private static String extraerValorJson(String json, String llave) {
        String patron = "\"" + llave + "\":\"";
        int inicio = json.indexOf(patron);
        if (inicio == -1) {
            patron = "\"" + llave + "\": \"";
            inicio = json.indexOf(patron);
        }
        if (inicio == -1) return "";
        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        return json.substring(inicio, fin);
    }
}
