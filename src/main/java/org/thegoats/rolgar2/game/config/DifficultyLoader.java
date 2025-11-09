package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

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

    public static DifficultyConfig loadDifficulty(Path difficultyPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                difficultyPath.toFile(),
                DifficultyConfig.class
        );
    }
}
