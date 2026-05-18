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
import java.util.logging.Logger;

/**
 * Acceso a datos simulado (Mock) para la gestión de usuarios persistidos en un archivo de texto.
 */
public class UsuarioDAOMock {

    private static final Logger logger = Logger.getLogger(UsuarioDAOMock.class.getName());
    private List<UsuarioDTO> usuarios;
    private final String RUTA_ARCHIVO = "usuarios.txt";
    
    /**
     * Inicializa el almacenamiento temporal en memoria y carga los usuarios del archivo.
     * Crea un administrador por defecto si el almacén está vacío.
     */
    public UsuarioDAOMock() {
        usuarios = new ArrayList<>();
        cargarUsuariosDesdeArchivo();
        
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
     * Lee las credenciales del archivo plano y las añade a la lista local.
     */
    private void cargarUsuariosDesdeArchivo() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            long idCounter = 1;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
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
    
    /**
     * Compara las credenciales introducidas con las existentes tras aplicar el cifrado.
     */
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

    /**
     * Valida las restricciones del nombre, contraseñas duplicadas y escribe el registro en memoria y disco.
     */
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
        
        usuarios.add(new UsuarioDTO(nuevoId, nombre.trim(), hash, Rol.USUARIO)); 
        guardarUsuarioEnArchivo(nombre.trim(), hash);
        
        logger.info("Usuario '" + nombre + "' registrado y guardado permanentemente en el archivo txt.");
        return null;
    }
    
    /**
     * Adjunta una nueva línea con la información del usuario al final del documento de texto.
     */
    private void guardarUsuarioEnArchivo(String nombre, String hash) {
        try (FileWriter fw = new FileWriter(RUTA_ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(nombre + "," + hash);
            
        } catch (IOException e) {
            logger.severe("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return new ArrayList<>(this.usuarios);
    }
    
    /**
     * Envía la estructura completa de los DTOs vigentes a los registros de consola.
     */
    public void imprimirUsuariosEnConsola() {
        logger.info("--- Iniciando lectura de usuarios en el Mock ---");
        
        if (this.usuarios.isEmpty()) {
            logger.warning("La lista de usuarios está vacía.");
            return;
        }

        for (UsuarioDTO usuario : this.usuarios) {
            logger.info(usuario.toString());
        }
        
        logger.info("--- Fin de lectura ---");
    }
}
