package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Efecto de mitad de daño: reduce el daño recibido a la mitad
 */
public class HalfDamageEffect extends StatusEffect {
    /**
     * Construye el efecto con character como objetivo y duracion 'duration'
     * @param character no null, personaje al que se le aplicara el efecto
     * @param duration mayor a cero
     */
    public HalfDamageEffect(CharacterData character, int duration){
        super(character, duration);
    }

    /**
     * Aplica el efecto sobre character (ver padre)
     */
    @Override
    public void onApply() {
        getCharacter().setIncomingDamageFactor(getCharacter().getIncomingDamageFactor()/2);
    }

    /**
     * Remueve el efecto de character (ver padre)
     */
    @Override
    public void onRemove() {
        getCharacter().setIncomingDamageFactor(getCharacter().getIncomingDamageFactor()*2);
    }

    /**
     * @return version en formato String del efecto
     */
    @Override
    public String toString() {
        return String.format(
                "HalfDamageEffect[character=%s, remainingTurns=%d]",
                getCharacter(),
                getRemainingTurns()
        );
    }
}
