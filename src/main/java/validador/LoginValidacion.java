/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validador;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Clase utilitaria encargada de la validación de credenciales
 * y el cifrado de contraseñas mediante hash SHA-256.
 * @author Jazmin
 */
public class LoginValidacion {

    private static final Logger LOG = Logger.getLogger(LoginValidacion.class.getName());
    
    /**
     * Valida que el nombre de usuario y la contraseña cumplan con los requisitos mínimos.
     * @param nombre nombre a validar 
     * @param contrasenia contraseña a validar
     * @return null si las credenciales son validas o un mensaje de error en caso contrario
     */
    public static String validarUsuario(String nombre, String contrasenia) {
        //validar campos vacios
        if (nombre.isEmpty() || contrasenia.isEmpty()) {
            LOG.warning("Nombre y contraseña vacios");
            return "Los campos no pueden estar vacios";
        }
        //nombres minimo 3 letras 
        //validar caracteres de la contraseña , minimo una letra , un numero y un caracter especial
        // y al menos 8 caracteres
        if (nombre.trim().length() < 3 || !contrasenia.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+]).*")) {
            LOG.warning("Nombre o contraseña inválida");
            return "El usuario debe tener mínimo 3 caracteres "
                    + "y la contraseña debe tener letras, números y un carácter especial";
        }
        LOG.info("Usuario validado con exito");
        return null;

    }
    /**
     *  Genera un hash SHA-256 del texto recibido y lo retorna en formato hexadecimal.
     * Se utiliza para cifrar contraseñas antes de almacenarlas o compararlas.
     * @param texto texto a hashear
     * @return cadena hexadecimal representando el hash SHA-256 del texto
     * @throws NoSuchAlgorithmException si el algoritmo SHA-256 no está disponible.
     */
    public static String hashear(String texto) throws NoSuchAlgorithmException {
        //crea el objeto 
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        //convierte el texto a bytes , despues aplica el hash y regresa un arreglo de bytes
        byte[] hash = md.digest(texto.getBytes(StandardCharsets.UTF_8));
        //stringBuilder para convertirlo a hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
