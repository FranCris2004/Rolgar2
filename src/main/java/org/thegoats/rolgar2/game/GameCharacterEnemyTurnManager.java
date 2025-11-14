package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

public class GameCharacterEnemyTurnManager extends GameCharacterTurnManager {
    public GameCharacterEnemyTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(!gameCharacter.isPlayerCharacter(), "gameCharacter no debe ser un personaje de un jugador.");
    }

    @Override
    public void doTurn() {
        gameCharacter.game.logger.logDebug("El enemigo " + gameCharacter.characterData.getName() + " realiza su turno.");

        // busca un personaje de jugador en una celda vecina, si lo encuentra lo ataca
        gameCharacter.getWorldCell().getNeighbors().stream()
                .filter(cell -> cell.getCharacter().isPresent() && cell.getCharacter().get().isPlayerCharacter())
                .findAny()
                .ifPresent(worldCell -> {
                    gameCharacter.attack(worldCell.getCharacter().get());
                });
    }
}
