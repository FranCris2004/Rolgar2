package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;

import java.util.Set;

public final class Game {
    private int turnCount = 0;
    private final Logger logger;
    private final DifficultyData difficulty;
    private final MapData map;
    private final Set<GameCharacter> gameCharacters;

    /**
     * Crea Game dado un logger para impresiones, dificultad, mapa y personajes.
     * @param logger interfaz que representa un sistema de Logs
     * @param difficulty configuracion general de la dificultad
     * @param map informacion general del mapa
     * @param gameCharacters conjunto de GameCharacters
     */
    public Game(Logger logger, DifficultyData difficulty, MapData map, Set<GameCharacter> gameCharacters) {
        Assert.notNull(logger, "Logger no puede ser null");
        Assert.notNull(difficulty, "Difficulty no puede ser null");
        Assert.notNull(map, "Map no puede ser null");
        Assert.notNull(gameCharacters, "GameCharacters no puede ser null");
        Assert.isTrue(gameCharacters.size() >= 2, "No se puede jugar si no hay al menos dos jugadores");
        this.logger = logger;
        this.difficulty = difficulty;
        this.map = map;
        this.gameCharacters = gameCharacters;
    }

        /**
         * Construc de copia
         * @param game
         *  copia al objeto
         */
    public Game(Game game) {
            Assert.notNull(game, "Game no puede ser null");
            this.turnCount = game.turnCount;
            this.logger = game.logger;
            this.difficulty = game.difficulty;
            this.map = game.map;
            this.gameCharacters = game.gameCharacters;
    }

        /**
         *
         * @return devuelve la dificultad
         */
    public DifficultyData getDifficulty() {
            return difficulty;
    }
        /**
         *
         * @return devuelve la dificultad
         */
    public MapData getMap() {
            return map;
    }

    public Logger getLogger() {
            return logger;
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
        logger.logDebug("Game run");

        while (hasNextTurn()) {
            logger.logDebug("Game loop");
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
            gameCharacter.getTurnManager().doTurn();
        }
    }
}
