package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public final class MapLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private MapLoader() {}

    public static Set<MapConfig> loadMaps(String path) throws IOException, URISyntaxException {
        return loadFromResourceDirectory(path);
    }

    private static Set<MapConfig> loadFromResourceDirectory(String resourceDirName) throws IOException, URISyntaxException {
        Set<MapConfig> configs = new HashSet<>();

        try {
            // Intento 1: estamos dentro del IDE → el recurso es un directorio real en target/classes
            Path dir = Paths.get(
                    MapLoader.class.getClassLoader()
                            .getResource(resourceDirName)
                            .toURI()
            );

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path path : stream) {
                    configs.add(loadOne(path));
                }
            }
            return configs;

        } catch (URISyntaxException | NullPointerException e) {
            // Intento 2: estamos dentro de un JAR → los recursos NO son archivos reales
        }

        // En JAR: listamos recursos manualmente
        try (FileSystem fileSystem = getJarFileSystem()) {
            Path dir = fileSystem.getPath("/" + resourceDirName);

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path path : stream) {
                    try (InputStream in = MapLoader.class.getResourceAsStream("/" + resourceDirName + "/" + path.getFileName())) {
                        configs.add(mapper.readValue(in, MapConfig.class));
                    }
                }
            }
        }

        return configs;
    }

    private static MapConfig loadOne(Path path) throws IOException {
        return mapper.readValue(path.toFile(), MapConfig.class);
    }

    private static FileSystem getJarFileSystem() throws IOException, URISyntaxException {
        try {
            return FileSystems.getFileSystem(MapLoader.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());
        } catch (Exception e) {
            return FileSystems.newFileSystem(
                    MapLoader.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI(),
                    java.util.Collections.emptyMap()
            );
        }
    }
}
