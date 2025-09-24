package org.thegoats.rolgar2.entity.character.player;

import org.thegoats.rolgar2.entity.character.Character;
import org.thegoats.rolgar2.entity.character.ICharacterController;

public abstract class PlayerController implements ICharacterController {
    private Character character;

    @Override
    public void doTurn() {

        int actions = 3;
        for (int i = 0; i < actions; i++) {
            switch (selectAction(character)) {

            }
        }
    }

    public String selectAction(Character character) {
        return null;
    }

    public void move(Character character) {};

    public void attack(Character character) {};

    public void alliance(Character character) {};
}
