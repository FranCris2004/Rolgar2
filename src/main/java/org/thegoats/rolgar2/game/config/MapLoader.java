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

    /**
     * Carga todos los mapas (MapConfig) ubicados en el directorio indicado.
     * @param path nombre o ruta del directorio de recursos donde se encuentran los JSON de mapas
     * @return un conjunto de objetos MapConfig cargados desde ese directorio
     * @throws IOException si ocurre un error de lectura de archivos
     * @throws URISyntaxException si hay problemas al convertir la URL del recurso a URI
     */
    public static Set<MapConfig> loadMaps(String path) throws IOException, URISyntaxException {
        return loadFromResourceDirectory(path);
    }

    /**
     * Carga todos los MapConfig que se encuentran dentro de un directorio de recursos
     * @param resourceDirName  nombre del directorio de recursos
     * @return un conjunto de objetos MapConfig leídos desde los archivos JSON del directorio
     * @throws IOException  si ocurre un error de lectura de archivos
     * @throws URISyntaxException si hay problemas al resolver la ubicación del recurso
     */
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

    /**
     * Carga un único MapConfig desde el archivo JSON indicado por la ruta.
     * @param path Ruta del archivo JSON que contiene la configuración del mapa
     * @return un objeto MapConfig generado a partir del contenido del archivo
     * @throws IOException si ocurre un error de lectura o el JSON es inválido
     */
    private static MapConfig loadOne(Path path) throws IOException {
        return mapper.readValue(path.toFile(), MapConfig.class);
    }

    /**
     * Devuelve un FileSystem asociado al JAR desde el que se está ejecutando la clase MapLoader.
     * @return un FileSystem que representa el JAR actual
     * @throws IOException si ocurre un error al abrir o crear el FileSystem
     * @throws URISyntaxException si hay problemas al obtener la URI del JAR
     */
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
