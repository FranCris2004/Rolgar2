package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class MapLoader {
    /**
     * Carga todas las configuraciones de mapas,
     * cada mapa cargado se almacena en un conjunto que luego se devuelve
     * @param mapsDirectoryPath ruta al directorio que contiene los archivos de mapas
     * @return Devuelve un conjunto de objetos MapConfig
     * @throws IOException si ocurre un error al acceder al directorio o al leer alguno de los archivos
     */
    public static Set<MapConfig> loadMaps(Path mapsDirectoryPath) throws IOException {
        Set<MapConfig> maps = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mapsDirectoryPath)) {
            for (Path path : stream) {
                maps.add(loadMap(path));
            }
        }

        return maps;
    };

    /**
     * Carga y devuelve un mapa desde un archivo específico
     * @param mapPath ruta del archivo que contiene la configuración del mapa
     * @return Devuelve un objeto MapConfig construido a partir del contenido del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public static MapConfig loadMap(Path mapPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                mapPath.toFile(),
                MapConfig.class
        );
    }
}
