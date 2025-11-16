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

public class MapLoader {

    public static Set<MapConfig> loadMaps(Path mapsDirectoryPath) throws IOException {
        Set<MapConfig> maps = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mapsDirectoryPath)) {
            for (Path path : stream) {
                maps.add(loadMap(path));
            }
        }

        return maps;
    }

    public static MapConfig loadMap(Path mapPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mapPath.toFile(), MapConfig.class);
    }

    public static MapConfig loadMap(InputStream in) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(in, MapConfig.class);
    }

    public static Set<MapConfig> loadMaps(String resourceDirectory) throws IOException, URISyntaxException {
        Set<MapConfig> maps = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        ClassLoader cl = MapLoader.class.getClassLoader();
        URL url = cl.getResource(resourceDirectory);
        if (url == null) {
            throw new IOException("No se encontró el directorio de mapas: " + resourceDirectory);
        }

        // Caso 1: filesystem directo (IDE, target/classes)
        if (url.getProtocol().equals("file")) {
            Path dir = Path.of(url.toURI());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path path : stream) {
                    maps.add(loadMap(path));
                }
            }
            return maps;
        }

        // Caso 2: dentro de un JAR
        if (url.getProtocol().equals("jar")) {
            String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
            try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();

                    if (entry.getName().startsWith(resourceDirectory + "/") && !entry.isDirectory()) {
                        try (InputStream in = jar.getInputStream(entry)) {
                            MapConfig map = loadMap(in);
                            maps.add(map);
                        }
                    }
                }
            }
            return maps;
        }

        throw new IOException("Protocolo no soportado: " + url.getProtocol());
    }
}
