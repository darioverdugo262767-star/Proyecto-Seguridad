package mappers;

import dto.MensajeDTO;
import dto.Tipo;
import dto.UsuarioDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 *
 * @author Dario
 */
public class MensajeMapper {
    private static final Logger logger = Logger.getLogger(MensajeMapper.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String toPythonJson(MensajeDTO mensaje, String destinatario) {
        String nombreEmisor = (mensaje.getEmisor() != null) ? mensaje.getEmisor().getNombre() : "Desconocido";
        
        String fechaFormateada = "";
        if (mensaje.getFecha() != null) {
            fechaFormateada = mensaje.getFecha().format(FORMATTER);
        }

        String contenidoLimpio = mensaje.getContenido().replace("\"", "\\\"");

        return "{"
            + "\"type\":\"message\","
            + "\"from\":\"" + nombreEmisor + "\","
            + "\"to\":\"" + destinatario + "\"," 
            + "\"text\":\"" + contenidoLimpio + "\","
            + "\"timestamp\":\"" + fechaFormateada + "\""
            + "}";
    }

    public static MensajeDTO toMensajeDTO(String json) {
        try {
            String text = extraerValorJson(json, "text");
            String from = extraerValorJson(json, "from");
            String to = extraerValorJson(json, "to");
            
            //extrae la cadena del timestamp directo del JSON de Python
            String timestampStr = extraerValorJson(json, "timestamp");
            
            //crea la variable de fecha, si viene vacia se usa la actual como respaldo
            LocalDateTime fechaReal;
            if(timestampStr != null && !timestampStr.isEmpty()){
                // traduce el string de  python ("dd/MM/yyyy HH:mm:ss") a un objeto LocalDateTime de java
                fechaReal = LocalDateTime.parse(timestampStr, FORMATTER);
            } else{
                fechaReal = LocalDateTime.now();
            }
            
            UsuarioDTO emisor = new UsuarioDTO(0L, from, "", null); 
            Tipo tipo = "ALL".equalsIgnoreCase(to) ? Tipo.PUBLICO : Tipo.PRIVADO; 
            //se le pasa la fecha real que se extrajo del json al constructor
            return new MensajeDTO(text, emisor, LocalDateTime.now(), tipo);
        } catch (Exception e) {
            logger.severe("Error al mapear el mensaje JSON: " + e.getMessage());
            return null;
        }
    }

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
