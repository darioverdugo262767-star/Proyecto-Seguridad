package mappers;

import dto.MensajeDTO;
import dto.Tipo;
import dto.UsuarioDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Dario
 */
public class MensajeMapper {
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
            
            UsuarioDTO emisor = new UsuarioDTO(0L, from, "", null); 
            
            Tipo tipo = "ALL".equalsIgnoreCase(to) ? Tipo.PUBLICO : Tipo.PRIVADO; 

            return new MensajeDTO(text, emisor, LocalDateTime.now(), tipo);
        } catch (Exception e) {
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
