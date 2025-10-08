package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Hace al personaje invisible
 */
public class InvisibilityEffect extends StatusEffect {
    public InvisibilityEffect(int remainingTurns) {
        super(remainingTurns);
    }

    @Override
    public void onApply(CharacterData character) {
        character.setVisible(false);
    }

    @Override
    public void onRemove(CharacterData character) {
        character.setVisible(true);
    }

    @Override
    public String toString() {
        return String.format(
                "InvisibilityEffect[remainingTurns=%d]",
                getRemainingTurns()
        );
    }
}
