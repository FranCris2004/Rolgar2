package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.util.Assert;

public class GameCharacterPlayerTurnManager extends GameCharacterTurnManager {
    public GameCharacterPlayerTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(gameCharacter.isPlayerCharacter(), "gameCharacter debe ser un personaje de un jugador.");
    }

    @Override
    public void doTurn() {
        gameCharacter.game.logger.logDebug("El jugador " + gameCharacter.characterData.getName() + " realiza su turno.");
    }
}
