package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.game.config.*;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.GameUtils;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.Options;
import org.thegoats.rolgar2.util.io.ConsoleSelection;
import org.thegoats.rolgar2.util.io.Selection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GameBuilder {
    private Logger logger;
    private Random random;
    private Set<Player> players;
    private DifficultyConfig difficultyConfig;
    private MapConfig mapConfig;

    public GameBuilder() {}

    public static GameBuilder createBuilder() {
        return new GameBuilder();
    }

    public Game build() {
        Assert.notNull(players, "players");
        Assert.notNull(logger, "logger");
        Assert.notNull(difficultyConfig, "difficultyConfig");
        Assert.notNull(mapConfig, "mapConfig");

        if (random == null) {
            random = new Random();
        }

        return new Game(logger, random, players, new GameConfig(difficultyConfig, mapConfig));
    }

    public GameBuilder setLogger(Logger logger) {
        Assert.notNull(logger, "El logger no puede ser nulo.");
        this.logger = logger;
        return this;
    }

    public GameBuilder setRandom(Random random) {
        Assert.notNull(random, "El random no puede ser nulo.");
        this.random = random;
        return this;
    }

    public GameBuilder setDifficultyConfig(DifficultyConfig difficultyConfig) {
        Assert.notNull(difficultyConfig, "difficultyConfig");
        this.difficultyConfig = difficultyConfig;
        return this;
    }

    public GameBuilder selectDifficulty(String difficultiesDirectoryPath) {
        Set<DifficultyConfig> difficulties = null;
        try {
            difficulties = DifficultyLoader.loadDifficulties(Path.of(difficultiesDirectoryPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.logDebug("Difficulties: " + difficulties);

        difficultyConfig = new ConsoleSelection<DifficultyConfig>()
                .maxTries(10)
                .selectionHeader("Seleccione la dificultad: ")
                .selectionRetryMessage("Dificultad invalida, vuelva a intentarlo.")
                .selectionFailMessage("Demasiados intentos para seleccionar la dificultad.")
                .addAllOptions(difficulties.stream().toList())
                .select().orElseThrow(() -> new RuntimeException("No se pudo seleccionar la dificultad."));

        logger.logDebug("Selected difficulty: " + difficultyConfig);

        return this;
    }

    public GameBuilder setMapConfig(MapConfig mapConfig) {
        Assert.notNull(mapConfig, "mapConfig");
        this.mapConfig = mapConfig;
        return this;
    }

    public GameBuilder selectMap(String mapsDirectoryPath) {
        Set<MapConfig> maps = null;
        try {
            maps = MapLoader.loadMaps(mapsDirectoryPath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        logger.logDebug("Maps: " + maps);

        mapConfig = new ConsoleSelection<MapConfig>()
                .maxTries(10)
                .selectionHeader("Seleccione el mapa: ")
                .selectionRetryMessage("Mapa invalido, vuelva a intentarlo.")
                .selectionFailMessage("Demasiados intentos para seleccionar el mapa.")
                .addAllOptions(maps.stream().toList())
                .select().orElseThrow(() -> new RuntimeException("No se pudo seleccionar el mapa."));

        logger.logDebug("Selected map: " + mapConfig);

        return this;
    }

    public GameBuilder setPlayers(Set<Player> players) {
        Assert.notNull(players, "players");
        Assert.isTrue(!players.isEmpty(), "players no debe estar vacio.");
        this.players = players;
        return this;
    }

    public GameBuilder initPlayers() {
        Scanner scanner = new Scanner(System.in);

        // TODO: implementar alguna clase intermedia para tomar el input con reintentos similar a los de Select
        System.out.println("Cantidad de jugadores:");
        int playerCount = Integer.parseInt(scanner.nextLine());

        players = new org.thegoats.rolgar2.util.structures.sets.Set<>();
        for (int i = 0; i < playerCount; i++) {

            System.out.print("Nombre del jugador " + (i + 1) + ": ");
            var playerName = scanner.nextLine();

            int maxRetries = 5;
            while (maxRetries-- > 0 && !GameUtils.validName(playerName)) {
                System.out.println("Nombre del jugador " + (i + 1) + " invalido. Intentelo nuevamente.");
                System.out.print("Nombre del jugador " + (i + 1) + ": ");
                playerName = scanner.nextLine();
            }

            if (!GameUtils.validName(playerName)) {
                throw new RuntimeException("Demasiados intentos al nombrar al jugador.");
            }

            players.add(new Player(playerName));
        }

        return this;
    }
}
