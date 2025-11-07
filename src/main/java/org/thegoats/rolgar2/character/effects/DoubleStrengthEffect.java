package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;
import org.thegoats.rolgar2.util.Assert;

/**
 * Duplica la fuerza de un personaje durante 'duration' turnos
 */
public class DoubleStrengthEffect extends StatusEffect {
    /**
     * Construye el efecto con un personaje destino y una duración de 'duration' turnos
     * @param character no null, personaje al cual aplicarle el efecto
     * @param duration mayor a cero, duracion del efecto en turnos
     */
    public DoubleStrengthEffect(CharacterData character, int duration) {
        super(character, duration);
    }

    /**
     * Aplica el efecto sobre character (ver padre)
     */
    @Override
    public void onApply() {
        Assert.notNull(getCharacter(), "character no puede ser null");
        getCharacter().setStrength(getCharacter().getStrength()*2);
    }

    /**
     * Remueve el efecto de character (ver padre)
     */
    @Override
    public void onRemove() {
        Assert.notNull(getCharacter(), "character no puede ser null");
        getCharacter().setStrength(getCharacter().getStrength()/2);
    }

    /**
     * @return version en formato String del efecto
     */
    @Override
    public String toString(){
        return String.format("DoubleStrengthEffect[character=%s, remainingTurns=%d]",
                getCharacter(),
                getRemainingTurns());
    }
}
