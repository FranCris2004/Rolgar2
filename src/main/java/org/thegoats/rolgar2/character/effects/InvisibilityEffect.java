package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Efecto de invisibilidad: hace al personaje invisible durante 'duration' turnos
 */
public class InvisibilityEffect extends StatusEffect {
    /**
     * Construye el efecto con character como objetivo y duracion 'duration'
     * @param character no null, personaje al que se le aplicará el efecto
     * @param duration mayor a cero, duracion en turnos del efecto
     */
    public InvisibilityEffect(CharacterData character, int duration) {
        super(character, duration);
    }

    /**
     * Aplica el efecto sobre character (ver padre)
     */
    @Override
    public void onApply() {
        getCharacter().setVisible(false);
    }

    /**
     * Remueve el efecto de character (ver padre)
     */
    @Override
    public void onRemove() {
        getCharacter().setVisible(true);
    }

    /**
     * @return version en formato String del efecto
     */
    @Override
    public String toString() {
        return String.format(
                "InvisibilityEffect[character=%s, remainingTurns=%d]",
                getCharacter(),
                getRemainingTurns()
        );
    }
}
