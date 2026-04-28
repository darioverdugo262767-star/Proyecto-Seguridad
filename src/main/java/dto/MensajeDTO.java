package dto;

import java.time.LocalDateTime;

/**
 *
 * @author Dario
 */
public class MensajeDTO {
   private String contenido;
   private UsuarioDTO emisor;
   private LocalDateTime fecha;
   private Tipo tipo;

    public MensajeDTO(String contenido, UsuarioDTO emisor, LocalDateTime fecha, Tipo tipo) {
        this.contenido = contenido;
        this.emisor = emisor;
        this.fecha = fecha;
        this.tipo = tipo;
    }
   
    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public UsuarioDTO getEmisor() {
        return emisor;
    }

    public void setEmisor(UsuarioDTO emisor) {
        this.emisor = emisor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
   
   
}
