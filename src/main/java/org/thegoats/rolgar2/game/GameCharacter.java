package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

public final class GameCharacter {
    public final boolean isPlayer;
    public final CharacterData characterData;
    private Position position;

    public GameCharacter(CharacterData characterData, Position position, boolean isPlayer) {
        Assert.notNull(characterData, "characterData no puede ser nulo");
        this.characterData = characterData;
        this.isPlayer = isPlayer;
        setPosition(position);
    }

    public void setPosition(Position position) {
        Assert.notNull(position, "position no puede ser nulo.");
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
