package daoMock;

import dto.Rol;
import dto.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dario
 */
public class UsuarioDAOMock {
    private List<UsuarioDTO> usuarios;

    public UsuarioDAOMock() {
        usuarios = new ArrayList<>();
        
        usuarios.add(new UsuarioDTO(1L, "admin", "1234", Rol.ADMIN));
        usuarios.add(new UsuarioDTO(2L, "pepe", "abcd", Rol.USUARIO));
    }

    public UsuarioDTO validarUsuario(String nombre, String contrasenia) {
        for (UsuarioDTO u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }
}
