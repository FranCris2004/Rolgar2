package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.util.Assert;

public class GameCharacterEnemyTurnManager extends GameCharacterTurnManager {
    public GameCharacterEnemyTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(!gameCharacter.isPlayerCharacter(), "gameCharacter no debe ser un personaje de un jugador.");
    }

    @Override
    public void doTurn() {
        gameCharacter.getGame().getLogger().logDebug("El enemigo " + gameCharacter.getCharacterData().getName() + " realiza su turno.");
    }
}
