package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Duplica la fuerza de un personaje momentaneamente
 */
public class DoubleStrengthEffect extends StatusEffect {
    public DoubleStrengthEffect(CharacterData character, int remainingTurns) {
        super(character, remainingTurns);
    }

    @Override
    public void onApply() {
        CharacterData character = getCharacter();
        character.setStrength(character.getStrength()*2);
    }

    @Override
    public void onRemove() {
        CharacterData character = getCharacter();
        character.setStrength(character.getStrength()/2);
    }
}
