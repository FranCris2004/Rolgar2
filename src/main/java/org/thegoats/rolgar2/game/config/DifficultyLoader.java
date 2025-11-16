package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DifficultyLoader {
    public static Set<DifficultyConfig> loadDifficulties(Path difficultiesDirectoryPath) throws IOException {
        Set<DifficultyConfig> difficulties = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(difficultiesDirectoryPath)) {
            for (Path path : stream) {
                difficulties.add(loadDifficulty(path));
            }
        }

        return difficulties;
    };

    public static DifficultyConfig loadDifficulty(InputStream in) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(in, DifficultyConfig.class);
    }

    public static Set<DifficultyConfig> loadDifficulties(String resourceDirectory) throws IOException, URISyntaxException {
        Set<DifficultyConfig> difficulties = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        ClassLoader cl = DifficultyLoader.class.getClassLoader();
        URL url = cl.getResource(resourceDirectory);
        if (url == null) {
            throw new IOException("No se encontró el directorio de dificultades: " + resourceDirectory);
        }

        // Caso 1: ejecución desde IDE o target/classes (filesystem normal)
        if (url.getProtocol().equals("file")) {
            Path dir = Path.of(url.toURI());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path path : stream) {
                    difficulties.add(loadDifficulty(path));
                }
            }
            return difficulties;
        }

        // Caso 2: ejecución dentro de un JAR
        if (url.getProtocol().equals("jar")) {
            String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
            try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith(resourceDirectory + "/") && !entry.isDirectory()) {

                        try (InputStream in = jar.getInputStream(entry)) {
                            DifficultyConfig cfg = loadDifficulty(in);
                            difficulties.add(cfg);
                        }
                    }
                }
            }
            return difficulties;
        }

        throw new IOException("Protocolo no soportado: " + url.getProtocol());
    }

    public static DifficultyConfig loadDifficulty(Path difficultyPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                difficultyPath.toFile(),
                DifficultyConfig.class
        );
    }
}
