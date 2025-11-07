package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;
import org.thegoats.rolgar2.util.Assert;

/**
 * Efecto de congelamiento aplicado por la carta IceBall
 */

public class FreezeEffect extends StatusEffect {
    /**
     * Construye el efecto de congelamiento con los turnos indicados
     * @param duration mayor a cero
     */
    public FreezeEffect(CharacterData character, int duration){
        super(character, duration);
    }

    /**
     * Guarda en un atributo auxiliar los movimientos por turno anteriores al congelamiento
     */
    @Override
    public void onApply() {
        Assert.notNull(getCharacter(), "character no puede ser null");
        getCharacter().freeze();
    }

    /**
     * Devuelve sus movimientos por turno al personaje
     */
    @Override
    public void onRemove() {
        Assert.notNull(getCharacter(), "character no puede ser null");
        getCharacter().unfreeze();
    }

    /**
     * @return version en formato String del efecto
     */
    @Override
    public String toString(){
        return String.format("FreezeEffect[character=%s, remainingTurns=%d]",
                getCharacter(),
                getRemainingTurns());
    }
}
