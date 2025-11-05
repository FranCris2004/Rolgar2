package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

/**
 * Interfaz de las clases CharacterController,
 * las cuales son las que contienen la logica de control del personaje y si es necesario, toman el input del usuario
 */
public abstract class CharacterController {
    private final CharacterData characterData;

    public CharacterController(CharacterData characterData) {
        Assert.notNull(characterData, "CharacterData no debe ser nulo");
        this.characterData = characterData;
    }

    protected CharacterData getCharacterData() {
        return characterData;
    }

    public abstract void doTurn();
}
