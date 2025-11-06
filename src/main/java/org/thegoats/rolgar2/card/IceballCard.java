package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.FreezeEffect;

/**
 * Carta que aplica un efecto de estado de congelamiento al 'target' indicado con 'duration' turnos.
 */
public class IceballCard extends CardWithStatusEffect {
    /**
     * Aplica el efecto de la carta sobre el 'target' indicado, con una duracion de 'duration' turnos
     */
    @Override
    public void use() {
        validateTarget();
        validateRemainingTurns();
        getTarget().applyEffect(new FreezeEffect(getTarget(), getRemainingTurns()));
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    public String toString(){
        return String.format("IceballCard[target=%s, duration=%d",
                getTarget().toString(),
                getRemainingTurns());
    }
}
