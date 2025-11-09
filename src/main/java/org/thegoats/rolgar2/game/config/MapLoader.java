package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class MapLoader {
    public static Set<MapConfig> loadMaps(Path mapsDirectoryPath) throws IOException {
        Set<MapConfig> maps = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(mapsDirectoryPath)) {
            for (Path path : stream) {
                maps.add(loadMap(path));
            }
        }

        return maps;
    };

    public static MapConfig loadMap(Path mapPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                mapPath.toFile(),
                MapConfig.class
        );
    }
}
