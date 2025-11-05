package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.WorldCell;

public final class GameCharacter {
    public final boolean isPlayer;
    public final CharacterData characterData;
    private WorldCell worldCell;

    public GameCharacter(CharacterData characterData, WorldCell initialWorldCell, boolean isPlayer) {
        Assert.notNull(characterData, "characterData no puede ser nulo");
        this.characterData = characterData;
        this.isPlayer = isPlayer;
        setWorldCell(initialWorldCell);
    }

    public void setWorldCell(WorldCell worldCell) {
        Assert.notNull(worldCell, "worldCell no puede ser nulo.");
        this.worldCell = worldCell;
    }

    public WorldCell getWorldCell() {
        return worldCell;
    }
}
