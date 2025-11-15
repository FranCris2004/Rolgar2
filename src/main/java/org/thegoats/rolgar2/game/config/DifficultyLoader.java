package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class DifficultyLoader {
    /**
     * Carga todas las configuraciones de dificultad desde un directorio que las contiene,
     * las agrega a un conjunto y devuelve el mismo
     * @param difficultiesDirectoryPath ruta al directorio que contiene los archivos de dificultad
     * @return Devuelve un conjunto de objetos DifficultyConfig
     * @throws IOException si ocurre algun error al acceder al directorio o al leer algun archivo
     */
    public static Set<DifficultyConfig> loadDifficulties(Path difficultiesDirectoryPath) throws IOException {
        Set<DifficultyConfig> difficulties = new org.thegoats.rolgar2.util.structures.sets.Set<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(difficultiesDirectoryPath)) {
            for (Path path : stream) {
                difficulties.add(loadDifficulty(path));
            }
        }

        return difficulties;
    };

    /**
     * Carga una configuracion de dificultad desde un archivo,
     * lo convierte a un objeto DifficultyConfig y lo devuelve
     * @param difficultyPath ruta del archivo que contiene la configuracion de dificultad
     * @return Devuelve un objeto DifficultyConfig construido a partir del contenido del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public static DifficultyConfig loadDifficulty(Path difficultyPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                difficultyPath.toFile(),
                DifficultyConfig.class
        );
    }
}
