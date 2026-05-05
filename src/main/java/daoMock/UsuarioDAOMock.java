package daoMock;

import dto.Rol;
import dto.UsuarioDTO;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import validador.LoginValidacion;

/**
 *
 * @author Dario
 */
public class UsuarioDAOMock {

    private List<UsuarioDTO> usuarios;

    public UsuarioDAOMock() {
        usuarios = new ArrayList<>();

    }

    public UsuarioDTO validarUsuario(String nombre, String contrasenia) throws NoSuchAlgorithmException {
        String contraseniaHash = LoginValidacion.hashear(contrasenia);
        for (UsuarioDTO u : usuarios) {
            if (u.getNombre().equals(nombre) && u.getContrasenia().equals(contraseniaHash)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Registra un nuevo usuario a la lista de usuarios
     *
     * @param nombre nombre de usuario
     * @param contrasena contraseña del usuario
     * @return null de que se registro
     * @throws NoSuchAlgorithmException por si ocurre algun error
     */
    public String registrarUsuario(String nombre, String contrasena) throws NoSuchAlgorithmException {
        //validar el formato
        String error = LoginValidacion.validarUsuario(nombre, contrasena);
        if (error != null) {
            return error;
        }
        //verificar que ese nombre de usuario no este en uso
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return "Este nombre de usuario ya está registrado";
            }
            
        }
        String hash = LoginValidacion.hashear(contrasena);
            usuarios.add(new UsuarioDTO(nombre, hash, Rol.USUARIO));
            return null;
    }
   
}
