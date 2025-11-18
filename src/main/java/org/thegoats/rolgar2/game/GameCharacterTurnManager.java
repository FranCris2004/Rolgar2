package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;

public abstract class GameCharacterTurnManager {
    protected final GameCharacter gameCharacter;
    protected final Logger logger;

    /**
     * Construye un administrador de turnos para el personaje dado.
     * @param gameCharacter personaje del juego, no debe ser nulo
     */
    public GameCharacterTurnManager(GameCharacter gameCharacter) {
        Assert.notNull(gameCharacter, "gameCharacter no puede ser nulo.");
        this.gameCharacter = gameCharacter;
        this.logger = gameCharacter.getGame().logger;
    }

    /**
     * Ejecuta el turno completo del personaje.
     */
    public abstract void doTurn();
}
