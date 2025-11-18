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
     * Carga todas las configuraciones de dificultad desde un directorio que las contiene,
     * las agrega a un conjunto y devuelve el mismo
     * @param directoryPath ruta al directorio que contiene los archivos de dificultad
     * @return Devuelve un conjunto de objetos DifficultyConfig
     * @throws IOException si ocurre algun error al acceder al directorio o al leer algun archivo
     */
    public static Set<DifficultyConfig> loadDifficulties(Path directoryPath) throws IOException {
        return loadDifficulties(directoryPath.toString());
    }

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
     * Carga una configuracion de dificultad desde un archivo,
     * lo convierte a un objeto DifficultyConfig y lo devuelve
     * @param difficultyPath ruta del archivo que contiene la configuracion de dificultad
     * @return Devuelve un objeto DifficultyConfig construido a partir del contenido del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public static DifficultyConfig loadDifficulty(Path path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(path.toFile(), DifficultyConfig.class);
    }

    // ------------------------------------------
    // Lectura desde JAR
    // ------------------------------------------
    public static DifficultyConfig loadDifficulty(InputStream in) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(in, DifficultyConfig.class);
    }
}
