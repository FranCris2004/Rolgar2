package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

import java.util.Random;

/**
 * Carta que vuelve invisible al personaje 'target' durante 'duration' turnos
 */

public class InvisibilityCard extends CardWithStatusEffect {
    public InvisibilityCard(int remainingTurns) {
        super(remainingTurns);
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
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

    public static class Factory extends CardWithStatusEffect.Factory<InvisibilityCard> {
        public Factory(Random random, int remainingTurnsFloor, int remainingTurnsRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
        }

        @Override
        public InvisibilityCard create() {
            return new InvisibilityCard(getRandomDuration());
        }
    }
}
