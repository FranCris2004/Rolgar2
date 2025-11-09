package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.game.config.*;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.Options;

import java.util.Scanner;
import java.util.Set;

public class GameBuilder {
    private Logger logger;
    private DifficultyConfig difficultyConfig;
    private MapConfig mapConfig;
    private Set<GameCharacter> characters;

    public GameBuilder() {}

    public Game build() {
        return new Game(logger, new GameConfig(difficultyConfig, mapConfig), characters);
    }

    public GameBuilder setLogger(Logger logger) {
        Assert.notNull(logger, "El logger no puede ser nulo.");
        this.logger = logger;
        return this;
    }

    public GameBuilder selectDifficulty(String difficultiesDirectoryPath) {
        var difficulties = DifficultyLoader.loadDifficulties(difficultiesDirectoryPath);

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

    public GameBuilder selectMap(String mapsDirectoryPath) {
        var maps = MapLoader.loadMaps(mapsDirectoryPath);

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

    public GameBuilder initPlayers(String playersDirectoryPath) {
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
