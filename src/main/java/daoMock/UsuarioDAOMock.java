package daoMock;

import dto.Rol;
import dto.UsuarioDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import validador.LoginValidacion;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dario
 */
public class UsuarioDAOMock {

    private static final Logger logger = Logger.getLogger(UsuarioDAOMock.class.getName());
    private List<UsuarioDTO> usuarios;
    private final String RUTA_ARCHIVO = "usuarios.txt"; // El archivo se creará en la raíz del proyecto de NetBeans
    
    public UsuarioDAOMock() {
        usuarios = new ArrayList<>();
        cargarUsuariosDesdeArchivo();
        
        // Si el archivo no existía o estaba vacío, creamos un admin por defecto para que no te quedes sin acceso
        if (usuarios.isEmpty()) {
            try {
                String hashAdmin = LoginValidacion.hashear("admin123");
                usuarios.add(new UsuarioDTO(1L, "admin", hashAdmin, Rol.USUARIO));
                guardarUsuarioEnArchivo("admin", hashAdmin);
                logger.info("Archivo vacío. Se creó el usuario 'admin' por defecto y se guardó en el .txt");
            } catch (NoSuchAlgorithmException e) {
                logger.severe("Error creando admin por defecto: " + e.getMessage());
            }
        }
    }
    
    /**
     * Lee el archivo de texto línea por línea y llena la lista en RAM al encender la app
     */
    private void cargarUsuariosDesdeArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hacemos nada, la lista queda vacía
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            long idCounter = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Asegurarnos de que la línea tiene el formato: nombre,hash
                if (datos.length >= 2) { 
                    String nombre = datos[0].trim();
                    String hash = datos[1].trim();
                    usuarios.add(new UsuarioDTO(idCounter++, nombre, hash, Rol.USUARIO));
                }
            }
            logger.info("Usuarios cargados exitosamente desde el archivo txt.");
        } catch (IOException e) {
            logger.severe("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }
    
    public UsuarioDTO validarUsuario(String nombre, String contrasenia) throws NoSuchAlgorithmException {
        String hashIntento = LoginValidacion.hashear(contrasenia.trim());

        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre.trim()) 
                    && usuario.getContrasenia().equals(hashIntento)) {
                logger.info("Autenticación exitosa para el usuario: " + nombre);
                return usuario;
            }
        }
        logger.warning("Intento de sesión fallido para el usuario: " + nombre);
        return null;
    }

    public String registrarUsuario(String nombre, String contrasena) throws NoSuchAlgorithmException {
        String error = LoginValidacion.validarUsuario(nombre, contrasena);
        if (error != null) {
            return error;
        }
        
        for (UsuarioDTO usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre.trim())) {
                return "Este nombre de usuario ya está registrado";
            }
        }
        
        String hash = LoginValidacion.hashear(contrasena);
        Long nuevoId = (long) (usuarios.size() + 1);
        
        // 1. Lo agregamos a la memoria RAM para que pueda iniciar sesión inmediatamente
        usuarios.add(new UsuarioDTO(nuevoId, nombre.trim(), hash, Rol.USUARIO)); 
        
        // 2. Lo persistimos escribiéndolo en el documento de texto
        guardarUsuarioEnArchivo(nombre.trim(), hash);
        
        logger.info("Usuario '" + nombre + "' registrado y guardado permanentemente en el archivo txt.");
        return null;
    }
    
    /**
     * Escribe una nueva línea al final del archivo txt sin borrar lo que ya estaba
     */
    private void guardarUsuarioEnArchivo(String nombre, String hash) {
        // El parámetro 'true' en FileWriter significa modo "Append" (Añadir al final)
        try (FileWriter fw = new FileWriter(RUTA_ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(nombre + "," + hash);
            
        } catch (IOException e) {
            logger.severe("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    
    /*
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
*/
    
    /**
     * Registra un nuevo usuario a la lista de usuarios
     *
     * @param nombre nombre de usuario
     * @param contrasena contraseña del usuario
     * @return null de que se registro
     * @throws NoSuchAlgorithmException por si ocurre algun error
     */
    /*
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
        Long nuevoId = (long) (usuarios.size() + 1);
        usuarios.add(new UsuarioDTO(nuevoId, nombre, hash, Rol.USUARIO)); 
        //se imprimen en los logs para pruebas
        imprimirUsuariosEnConsola();
        return null;
    }
    */
        public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        // Retornamos una nueva lista con los elementos actuales
        return new ArrayList<>(this.usuarios);
    }
    
    // 2. Método para imprimir todos los DTOs en la terminal con el Logger
    public void imprimirUsuariosEnConsola() {
        logger.info("--- Iniciando lectura de usuarios en el Mock ---");
        
        if (this.usuarios.isEmpty()) {
            logger.warning("La lista de usuarios está vacía.");
            return;
        }

        // Recorremos la lista y mandamos cada DTO al logger
        for (UsuarioDTO usuario : this.usuarios) {
            logger.info(usuario.toString());
        }
        
        logger.info("--- Fin de lectura ---");
    }

}
