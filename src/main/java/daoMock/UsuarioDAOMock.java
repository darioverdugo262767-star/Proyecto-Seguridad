package daoMock;

import dto.Rol;
import dto.UsuarioDTO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public UsuarioDTO validarUsuario(String nombre, String contrasenia) throws NoSuchAlgorithmException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));

        String linea;

        while ((linea = br.readLine()) != null) {

            String[] datos = linea.split(",");

            String usuario = datos[0].trim();
            String pass = datos[1].trim();

            if (usuario.equals(nombre.trim())
                    && pass.equals(contrasenia.trim())) {

                br.close();
                return new UsuarioDTO(usuario, pass, Rol.USUARIO);
            }
        }

        br.close();
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
        Long nuevoId = (long) (usuarios.size() + 1);
        usuarios.add(new UsuarioDTO(nuevoId, nombre, hash, Rol.USUARIO)); 
        return null;
    }

}
