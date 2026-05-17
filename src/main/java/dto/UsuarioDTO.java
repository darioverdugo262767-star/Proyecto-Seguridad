package dto;

/**
 *
 * @author Dario
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
