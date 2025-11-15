package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.game.config.*;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.Options;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;

public class GameBuilder {
    private Logger logger;
    private DifficultyConfig difficultyConfig;
    private MapConfig mapConfig;
    private Set<GameCharacter> characters;

    public GameBuilder() {}

    /**
     * Crea una nueva instancia de GameBuilder
     * @return Devuelve la instancia creada
     */
    public static GameBuilder createBuilder() {
        return new GameBuilder();
    }

    /**
     * Crea una nueva instancia de Game
     * @return Devuelve una instancia configurada de Game
     */
    public Game build() {
        return new Game(logger, new GameConfig(difficultyConfig, mapConfig), characters);
    }

    /**
     * Establece el logger que usara Game para registrar eventos
     * @param logger no puede ser nulo
     * @return Devuelve esta misma instancia de GameBuilder
     */
    public GameBuilder setLogger(Logger logger) {
        Assert.notNull(logger, "El logger no puede ser nulo.");
        this.logger = logger;
        return this;
    }

    /**
     * Permite al usuario seleccionar una dificultad desde un directorio que contiene las configuraciones válidas de dificultad
     * @param difficultiesDirectoryPath ruta al directorio que contiene los archivos de dificultad
     * @return Devuelve esta misma instancia de GameBuilder
     * @throws RuntimeException si ocurre un error al cargar las dificultades o si el usuario no selecciona ninguna
     */
    public GameBuilder selectDifficulty(String difficultiesDirectoryPath) {
        Set<DifficultyConfig> difficulties = null;
        try {
            difficulties = DifficultyLoader.loadDifficulties(Path.of(difficultiesDirectoryPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String difficultyName = new Options(
                "Seleccione la dificultad",
                (String[])difficulties.stream().map(DifficultyConfig::name).toArray(),
                "Dificultad invalida, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar una dificultad."));

        difficultyConfig = difficulties.stream()
                .filter(difficultyConfig -> difficultyConfig.name().equals(difficultyName))
                .findFirst()
                .orElseThrow();

        return this;
    }

    /**
     * Permite al usuario seleccionar un mapa desde un directorio que contiene las configuraciones válidas de mapas
     * @param mapsDirectoryPath ruta al directorio que contiene los archivos de mapa
     * @return Devuelve esta misma instancia de GameBuilder
     * @throws RuntimeException si ocurre un error al cargar los mapas o si el usuario no elige ninguno
     */
    public GameBuilder selectMap(String mapsDirectoryPath) {
        Set<MapConfig> maps = null;
        try {
            maps = MapLoader.loadMaps(Path.of(mapsDirectoryPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String mapName = new Options(
                "Seleccione el mapa",
                (String[])maps.stream().map(MapConfig::name).toArray(),
                "Mapa invalido, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar un mapa."));

        mapConfig = maps.stream()
                .filter(difficultyConfig -> difficultyConfig.name().equals(mapName))
                .findFirst()
                .orElseThrow();

        return this;
    }

    /**
     * Inicializa la cantidad de jugadores que ingrese el usuario
     * @return Devuelve esta misma instancia de GameBuilder
     */
    public GameBuilder initPlayers() {
        Scanner scanner = new Scanner(System.in);

        int playerCount = 3; // TODO: leerlo desde el input del usuario

        for (int i = 0; i < playerCount; i++) {
            System.out.print("Nombre del jugador " + (i + 1) + ":");
            var playerName = scanner.nextLine();
            Assert.validName(playerName, "Nombre del jugador " + (i + 1) + " invalido.");
        }

        return this;
    }
}
