package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;
import org.thegoats.rolgar2.util.Assert;

public class DoubleStrengthEffect extends StatusEffect {

    /**
     * Crea el efecto de estado
     * @param remainingTurns turnos a durar del efecto
     */
    public DoubleStrengthEffect(Character character, int remainingTurns) {
        super(character, remainingTurns);
    }

    /**
     * Aplica el efecto al personaje
     * @param character personaje objetivo
     */
    @Override
    public void onApply(CharacterData character) {
        Assert.notNull(character, "El personaje objetivo es null");
        character.setStrength(character.getStrength()*2);
    }

    /**
     * Quita efecto al personaje
     * @param character personaje objetivo
     */
    @Override
    public void onRemove(CharacterData character) {
        Assert.notNull(character, "El personaje objetico es null");
        character.setStrength(character.getStrength()/2);
    }

    /**
     *
     * @return version en formato string del efecto
     */
    @Override
    public String toString() {
        return String.format(
                "DoubleStrengthEffect[remainingTurns=%d]",
                getRemainingTurns()
        );
    }
}
