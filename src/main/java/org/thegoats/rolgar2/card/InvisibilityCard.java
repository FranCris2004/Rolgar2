package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

import java.util.Random;

/**
 * Carta que vuelve invisible al personaje 'target' durante 'duration' turnos
 */

public class InvisibilityCard extends CardWithStatusEffect {
    public InvisibilityCard(int duration) {
        super(duration);
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
    @Override
    public String toString(){
        return String.format("InvisibilityCard[target=%s, duration=%d]",
                getTarget(),
                getDuration());
    }

    /**
     * Fábrica de cartas de invisibilidad, construye la fábrica de
     * CardWithStatusEffect con un generador aleatorio, piso y techo de duraciones,
     * y a partir de ahi permite utilizar el metodo create() para abstraerse de la implementacion
     * y generar una carta cuyo efecto de estado a aplicar tenga duracion aleatoria
     */
    public static class Factory extends CardWithStatusEffect.Factory<InvisibilityCard> {

        /**
         * Construye la fabrica de cartas de invisibilidad
         * @param random no null, objeto generador aleatorio
         * @param durationFloor mayor a cero, menor a 'durationRoof'
         * @param durationRoof mayor a cero, mayor a 'durationFloor'
         */
        public Factory(Random random, int durationFloor, int durationRoof) {
            super(random, durationFloor, durationRoof);
        }

        /**
         * Crea una carta de invisibilidad cuyo efecto de estado tiene una duración aleatoria entre
         * 'durationFloor' y 'durationRoof' pasados por parámetro al constructor de Factory
         * @return carta de invisibilidad
         */
        @Override
        public InvisibilityCard create() {
            return new InvisibilityCard(getRandomDuration());
        }
    }
}
