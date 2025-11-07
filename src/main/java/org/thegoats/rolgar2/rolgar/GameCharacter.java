package org.thegoats.rolgar2.rolgar;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.WorldCell;

public final class GameCharacter {
    public final Player player;
    public final CharacterData characterData;
    private WorldCell worldCell;

    public GameCharacter(CharacterData characterData, WorldCell initialWorldCell, Player player) {
        Assert.notNull(characterData, "characterData no puede ser nulo");
        this.characterData = characterData;
        this.player = player;
        setWorldCell(initialWorldCell);
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
