package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.util.Assert;

public abstract class GameCharacterTurnManager {
    protected final GameCharacter gameCharacter;

    public GameCharacterTurnManager(GameCharacter gameCharacter) {
        Assert.notNull(gameCharacter, "gameCharacter no puede ser nulo.");
        this.gameCharacter = gameCharacter;
    }

    public abstract void doTurn();
}
