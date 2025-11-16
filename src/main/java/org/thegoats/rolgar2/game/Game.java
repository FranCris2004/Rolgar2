package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterFactory;
import org.thegoats.rolgar2.game.config.GameConfig;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.World;
import org.thegoats.rolgar2.world.WorldViewer;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class Game {
    private int turnCount = 0;
    public final Logger logger;
    public final GameConfig config;
    private final Set<GameCharacter> gameCharacters;
    private final World world;

    private final CharacterFactory playerCharacterFactory;
    private final CharacterFactory enemyCharacterFactory;

    public Game(GameConfig config, Logger logger, Random random, Set<Player> players) {
        Assert.notNull(logger, "logger no puede ser nulo.");
        Assert.notNull(config, "config no puede ser nulo.");
        Assert.isTrue(players != null && !players.isEmpty(), "playerCharacterNames no puede ser nulo o vacio.");

        this.logger = logger;
        this.config = config;
        this.world = config.mapConfig().generateWorld();
        this.playerCharacterFactory = new CharacterFactory(random, config.difficultyConfig().playerConfig());
        this.enemyCharacterFactory = new CharacterFactory(random, config.difficultyConfig().enemyConfig());

        this.gameCharacters = new HashSet<>();
        for (Player player : players) {
            this.gameCharacters.add(
                    new GameCharacter(
                            this,
                            player,
                            playerCharacterFactory.create(player.getName()),
                            world.getRandomEmptyWalkableCell(random),
                            GameCharacterPlayerTurnManager.class
                    )
            );
        }

        logger.logDebug("Game constructor:");
        logger.logDebug("logger: " + logger);
        logger.logDebug("config: " + config);
        logger.logDebug("gameCharacters: " + gameCharacters);
    }

    /**
     * Inicia la ejecucion del juego, esto implica:
     * Cargar las configuraciones y mapas
     * Realizar las impresiones de inicio de juego
     * Iniciar el bucle de juego
     * Realizar las impresiones de fin del juego
     */
    public void run() throws IOException {
        logger.logDebug("Game run:");

        var characterBitmap = new Bitmap(32, 32);
        characterBitmap.fill(Color.magenta);

        WorldViewer worldViewer = new WorldViewer(
                config.mapConfig().getFloorBitmapMap(),
                config.mapConfig().getWallBitmapMap(),
                new HashMap<>(),
                characterBitmap,
                Color.darkGray,
                new Color(0, 0, 0, 0),
                new Color(0, 0, 0, 180),
                100, 100
        );

        for (int layer = 0; layer < world.getLayerCount(); layer++) {
            worldViewer.showLayers(world, 0, layer);
        }

        while (hasNextTurn()) {
            nextTurn();
        }
    }

    /**
     * Evalua si debe haber un proximo turno o si el juego debe terminar
     * @return trues si hay un proximo turno, false si no lo hay
     */
    private boolean hasNextTurn()
    {
        // en el futuro debera comprobar las condiciones para que el juego siga corriendo
        return turnCount < 5;
    }

    /**
     * Todos los personajes hacen su turno
     */
    private void nextTurn()
    {
        logger.logDebug("Turn " + ++turnCount);
        for (GameCharacter gameCharacter : gameCharacters) {
            gameCharacter.turnManager.doTurn();
        }
    }
}
