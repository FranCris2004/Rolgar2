package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DifficultyLoader {

    /**
     * Carga todas las dificultades a partir de los archivos JSON ubicados en el directorio indicado
     * @param directoryPath ruta del directorio que contiene los JSON de dificultad
     * @return conjunto de configuraciones de dificultad leídas desde ese directorio
     * @throws IOException si ocurre un problema al acceder al directorio o leer los archivos
     */
    public static Set<DifficultyConfig> loadDifficulties(Path directoryPath) throws IOException {
        return loadDifficulties(directoryPath.toString());
    }

    /**
     * Carga todas las dificultades desde un directorio de recursos.
     * @param resourceDirectory ruta del directorio de recursos donde están los JSON de dificultad
     * @return conjunto de configuraciones de dificultad encontradas en ese directorio
     * @throws IOException si no se encuentra el directorio, el protocolo no es soportado o ocurre un error de lectura
     */
    public static Set<DifficultyConfig> loadDifficulties(String resourceDirectory) throws IOException {
        Set<DifficultyConfig> difficulties =
                new org.thegoats.rolgar2.util.structures.sets.Set<>();

        ClassLoader cl = DifficultyLoader.class.getClassLoader();
        URL url = cl.getResource(resourceDirectory);

        if (url == null) {
            throw new IOException("No se encontró el directorio de dificultades: " + resourceDirectory);
        }

        String protocol = url.getProtocol();

        // ------------------------------------------
        // Caso 1: Ejecutando en IntelliJ (file://)
        // ------------------------------------------
        if (protocol.equals("file")) {
            Path dir;
            try {
                dir = Path.of(url.toURI());
            } catch (URISyntaxException e) {
                throw new IOException(e);
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path path : stream) {
                    if (Files.isRegularFile(path) && path.toString().endsWith(".json")) {
                        difficulties.add(loadDifficulty(path));
                    }
                }
            }

            return difficulties;
        }

        // ------------------------------------------
        // Caso 2: Ejecutando desde JAR (jar://)
        // ------------------------------------------
        if (protocol.equals("jar")) {

            String path = url.getPath();
            String jarPath = path.substring(5, path.indexOf("!"));

            try (JarFile jarFile = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();

                    if (!entry.isDirectory()
                            && entry.getName().startsWith(resourceDirectory + "/")
                            && entry.getName().endsWith(".json")) {

                        try (InputStream in = jarFile.getInputStream(entry)) {
                            difficulties.add(loadDifficulty(in));
                        }
                    }
                }
            }

            return difficulties;
        }

        throw new IOException("Protocolo no soportado: " + protocol);
    }

    /**
     * Carga una única configuración de dificultad desde un archivo JSON ubicado en el filesystem
     * @param path ruta del archivo JSON que define la dificultad
     * @return un objeto DifficultyConfig creado a partir del contenido del archivo
     * @throws IOException si no se puede leer el archivo o el JSON es inválido
     */
    public static DifficultyConfig loadDifficulty(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(path.toFile(), DifficultyConfig.class);
    }

    // ------------------------------------------
    // Lectura desde JAR
    // ------------------------------------------

    /**
     * Carga una única configuración de dificultad leyendo los datos desde
     * un flujo de entrada (InputStream).
     * @param in flujo de entrada desde el cual se leen los datos JSON
     * @return un objeto DifficultyConfig creado a partir del contenido del flujo
     * @throws IOException si ocurre un error de lectura o el JSON es inválido
     */
    public static DifficultyConfig loadDifficulty(InputStream in) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(in, DifficultyConfig.class);
    }
}
