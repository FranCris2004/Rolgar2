package org.thegoats.rolgar2.game;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;


public final class DifficultyDataLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private DifficultyDataLoader() {} // evitar instanciación

    public static DifficultyData load(String path) {
        try {
            return MAPPER.readValue(new File(path), DifficultyData.class);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de dificultad: " + path, e);
        }
    }
    public static List<DifficultyData> loadAll(String path) {
        try {
            return MAPPER.readValue(new File(path),
                    new TypeReference<List<DifficultyData>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo lista de dificultades: " + path, e);
        }
    }

}