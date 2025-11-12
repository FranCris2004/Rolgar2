package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.game.config.GameConfig;
import org.thegoats.rolgar2.util.Logger;

import java.util.Set;

public final class Game {
    private int turnCount = 0;
    public final Logger logger;
    public final GameConfig config;
    private final Set<GameCharacter> gameCharacters;

    public Game(Logger logger, GameConfig config, Set<GameCharacter> gameCharacters) {
        this.logger = logger;
        this.config = config;
        this.gameCharacters = gameCharacters;

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
    public void run()
    {
        logger.logDebug("Game run:");

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
