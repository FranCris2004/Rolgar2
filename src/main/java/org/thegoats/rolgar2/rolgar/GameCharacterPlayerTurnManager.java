package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.util.Assert;

public class GameCharacterPlayerTurnManager extends GameCharacterTurnManager {
    public GameCharacterPlayerTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(gameCharacter.isPlayerCharacter(), "gameCharacter debe ser un personaje de un jugador.");
    }

    @Override
    public void doTurn() {

    }
}
