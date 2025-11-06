package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

/**
 * Carta que vuelve invisible al personaje 'target' durante 'duration' turnos
 */

public class InvisibilityCard extends CardWithStatusEffect {
    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        validateDuration();
        getTarget().applyEffect(new InvisibilityEffect(getTarget(), getDuration()));
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    public String toString(){
        return String.format("InvisibilityCard[target=%s, duration=%d]",
                getTarget().toString(),
                getDuration());
    }
}
