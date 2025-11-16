package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterFactory;
import org.thegoats.rolgar2.game.config.*;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class GameBuilder {
    private Logger logger;
    private DifficultyConfig difficultyConfig;
    private MapConfig mapConfig;
    private Set<Player> players;
    private Random random;

    public GameBuilder() {}

    public static GameBuilder createBuilder() {
        return new GameBuilder();
    }

    public Game build() {
        if (this.difficultyConfig == null) {
            throw new RuntimeException("No se ha inicializado difficultyConfig. pruebe llamando a setDifficultyConfig o selectDifficultyConfig.");
        }

        if (this.mapConfig == null) {
            throw new RuntimeException("No se ha inicializado mapConfig. pruebe llamando a setMapConfig o selectMapConfig.");
        }

        if (players == null) {
            throw new RuntimeException("No se ha inicializado players. pruebe llamando initPlayers().");
        }

        if (logger == null) {
            logger = new ConsoleLogger(LogLevel.INFO);
        }

        return new Game(new GameConfig(difficultyConfig, mapConfig), logger, getRandom(), players);
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

    private Random getRandom() {
        if (random == null) {
            random = new Random();
        }

        return random;
    }

    public GameBuilder setDifficultyConfig(DifficultyConfig difficultyConfig) {
        Assert.notNull(difficultyConfig, "El difficultyConfig no puede ser nulo.");
        this.difficultyConfig = difficultyConfig;
        return this;
    }

    public GameBuilder selectDifficulty(String difficultiesDirectoryPath) {
        Set<DifficultyConfig> difficulties = null;
        try {
            difficulties = DifficultyLoader.loadDifficulties(difficultiesDirectoryPath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        logger.logDebug("Dificultades: " + Arrays.toString(difficulties.toArray()));

        String difficultyName = new Options(
                "Seleccione la dificultad",
                difficulties.stream()
                        .map(DifficultyConfig::name)
                        .toArray(String[]::new),
                "Dificultad invalida, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar una dificultad."));

        difficultyConfig = difficulties.stream()
                .filter(difficultyConfig -> difficultyConfig.name().equals(difficultyName))
                .findFirst()
                .orElseThrow();

        logger.logDebug("Dificultad seleccionada: " + difficultyConfig);

        return this;
    }

    public GameBuilder setMapConfig(MapConfig mapConfig) {
        Assert.notNull(mapConfig, "El mapConfig no puede ser nulo.");
        this.mapConfig = mapConfig;
        return this;
    }

    public GameBuilder selectMap(String mapsDirectoryPath) {
        logger.logDebug("Intentando seleccionar una mapa del directorio: " + mapsDirectoryPath);

        Set<MapConfig> maps;
        try {
            maps = MapLoader.loadMaps(mapsDirectoryPath);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        logger.logDebug("Maps: " + Arrays.toString(maps.toArray()));

        String mapName = new Options(
                "Seleccione el mapa",
                maps.stream()
                        .map(MapConfig::name)
                        .toArray(String[]::new),
                "Mapa invalido, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar un mapa."));

        mapConfig = maps.stream()
                .filter(mapConfig -> mapConfig.name().equals(mapName))
                .findFirst()
                .orElseThrow();

        logger.logDebug("Mapa seleccionada: " + mapConfig);

        return this;
    }

    public GameBuilder initPlayers() {
        logger.logDebug("Inicializando jugadores");

        Scanner scanner = new Scanner(System.in);

        int playerCount;
        System.out.print("Ingrese el numero de jugadores: ");
        playerCount = Integer.parseInt(scanner.nextLine());

        logger.logDebug("Cantidad de jugadores: " + playerCount);

        players = new HashSet<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            System.out.print("Nombre del jugador " + (i + 1) + ":");
            var playerName = scanner.nextLine();
            Assert.validName(playerName, "Nombre del jugador " + (i + 1) + " invalido.");
            players.add(new Player(playerName));
        }

        return this;
    }
}
