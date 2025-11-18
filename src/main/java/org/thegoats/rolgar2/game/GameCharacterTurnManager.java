package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;

public abstract class GameCharacterTurnManager {
    protected final GameCharacter gameCharacter;
    protected final Logger logger;

    public GameCharacterTurnManager(GameCharacter gameCharacter) {
        Assert.notNull(gameCharacter, "gameCharacter no puede ser nulo.");
        this.gameCharacter = gameCharacter;
        this.logger = gameCharacter.getGame().logger;
    }

    public abstract void doTurn();
}
