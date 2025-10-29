package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Hace al personaje invisible
 */
public class InvisibilityEffect extends StatusEffect {
    public InvisibilityEffect(CharacterData character, int remainingTurns) {
        super(character, remainingTurns);
    }

    @Override
    public void onApply() {
        getCharacter().setVisible(false);
    }

    @Override
    public void onRemove() {
        getCharacter().setVisible(true);
    }

    @Override
    public String toString() {
        return String.format(
                "InvisibilityEffect[remainingTurns=%d]",
                getRemainingTurns()
        );
    }
}
