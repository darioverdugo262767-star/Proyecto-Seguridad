package bo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dario
 */

public class FiltroPalabras {
    private static FiltroPalabras instancia;
    private final Set<String> repositorioMalasPalabras;

    private FiltroPalabras() {
        this.repositorioMalasPalabras = new HashSet<>();
        cargarRepositorio();
    }

    public static FiltroPalabras getInstancia() {
        if (instancia == null) {
            instancia = new FiltroPalabras();
        }
        return instancia;
    }

    private void cargarRepositorio() {
        String rutaArchivo = "malas_palabras.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String palabra = linea.trim().toLowerCase();
                if (!palabra.isEmpty()) {
                    repositorioMalasPalabras.add(palabra);
                }
            }
            System.out.println("[SISTEMA] Repositorio de palabras prohibidas cargado con éxito.");
        } catch (IOException e) {
            System.err.println("No se pudo cargar el repositorio de malas palabras: " + e.getMessage());
        }
    }

    public String censurarTexto(String textoOriginal) {
        if (textoOriginal == null || textoOriginal.isEmpty()) {
            return textoOriginal;
        }

        String[] palabras = textoOriginal.split("\\s+");
        StringBuilder textoCensurado = new StringBuilder();

        for (String palabra : palabras) {
            String palabraLimpia = palabra.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ]", "").toLowerCase();

            if (repositorioMalasPalabras.contains(palabraLimpia)) {
                String asteriscos = "*".repeat(palabra.length());
                textoCensurado.append(asteriscos).append(" ");
            } else {
                textoCensurado.append(palabra).append(" ");
            }
        }

        return textoCensurado.toString().trim();
    }
}
