package dto;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de un usuario del sistema.
 */
public class UsuarioDTO {
    private Long id;
    private String Nombre;
    private String contrasenia;
    private Rol rol;

    public UsuarioDTO(String Nombre, String contrasenia, Rol rol) {
        this.id = 0L;
        this.Nombre = Nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    
    /**
     * Crea una instancia de UsuarioDTO con todos sus atributos inicializados.
     */
    public UsuarioDTO(Long id, String Nombre, String contrasenia, Rol rol) {
        this.id = id;
        this.Nombre = Nombre;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    //Hash y toString por hacer si son necesarios.

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", Nombre=" + Nombre + ", contrasenia=" + contrasenia + ", rol=" + rol + '}';
    }
    
    
}
