package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;

public class PlayerCharacterController extends CharacterController {
    public PlayerCharacterController(CharacterData characterData) {
        super(characterData);
    }

    @Override
    public void doTurn() {
        CharacterData characterData = getCharacterData();

        System.out.println("Realiza su turno: " + characterData);
    }
}
