package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.WorldCell;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class GameCharacter {
    public final Game game;
    public final Player player;
    public final CharacterData characterData;
    public final GameCharacterTurnManager turnManager;
    private WorldCell worldCell;
    private static Bitmap bitmap;

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

    public void attack(GameCharacter character) {
        Assert.notNull(character, "character no puede ser nulo");

        game.logger.logInfo(this + " ataca a " + character);
        character.characterData.takeDamage(this.characterData.getStrength());
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void loadBitmapFromFile(String path) throws IOException {
        bitmap = Bitmap.loadFromFile(path);
    }

    @Override
    public String toString() {
        return characterData.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GameCharacter && Objects.equals(characterData, ((GameCharacter) o).characterData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterData);
    }
}
