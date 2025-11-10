package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.WorldCell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class GameCharacter {
    private final Game game;
    private final Player player;
    private final CharacterData characterData;
    private final GameCharacterTurnManager turnManager;
    private WorldCell worldCell;

    public GameCharacter(Game game, Player player, CharacterData characterData, WorldCell initialWorldCell, Class<? extends GameCharacterTurnManager> gameCharacterTurnManagerClass) {
        Assert.notNull(game, "game no puede ser nulo");
        Assert.notNull(characterData, "characterData no puede ser nulo");
        Assert.notNull(player, "player no puede ser nulo");
        setWorldCell(initialWorldCell);
        this.game = game;
        this.player = player;
        this.characterData = characterData;

        try {
            Constructor<?> ctor = gameCharacterTurnManagerClass.getDeclaredConstructor(GameCharacter.class);
            this.turnManager =  (GameCharacterTurnManager) ctor.newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Game getGame() {
            return game;
    }
    public Player getPlayer() {
            return player;
    }
    public CharacterData getCharacterData() {
            return characterData;
    }
    public GameCharacterTurnManager getTurnManager() {
            return turnManager;
    }

    public void setWorldCell(WorldCell worldCell) {
        Assert.notNull(worldCell, "worldCell no puede ser nulo.");
        this.worldCell = worldCell;
    }

    public WorldCell getWorldCell() {
        return worldCell;
    }

    public boolean isPlayerCharacter() {
        return player != null;
    }
}
