package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 * Muchas de las cartas que tienen un CharacterData como target, le aplican un efecto de estado StatusEffect a dicho personaje,
 * por lo tanto abstraemos en esta clase el atributo duration, que sera la duracion del efecto de estado en turnos,
 * y los metodos setDuration, getDuration, y validateDuration. Todas las cartas que apliquen un efecto
 * de estado heredaran de esta clase.
 */
public abstract class CardWithStatusEffect extends CardWithCharacterTarget {
    private final int duration;

    /**
     * Construye la carta cuyo efecto de estado tendrá 'duration' turnos
     * @param duration mayor a cero, duracion de la carta en turnos
     */
    public CardWithStatusEffect(int duration) {
        Assert.positive(duration, "duration debe ser positivo.");
        this.duration = duration;
    }

    /**
     * Devuelve la duracion que tendra la carta al aplicarse sobre el personaje en cuestion
     * @return duracion de la carta en turnos
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Recibe al construirse un piso y un techo para la duracion. De esta manera tendra un rango de valores para los que
     * generar una duracion aleatoria de la carta.
     * Dichos valores, piso y techo, serán extraídos del config.json
     * @param <T>
     */
    public static abstract class Factory<T extends CardWithStatusEffect> implements Card.Factory<T> {
        protected final Random random;
        protected final int durationFloor;
        protected final int durationRoof;

        /**
         * Recibe el piso y techo y los setea, recibe el generador aleatorio y lo setea. Valida los tres parametros.
         * @param random objeto de tipo Random, generador aleatorio en este caso utilizado para generar duraciones de
         *               los efectos de estado
         * @param durationFloor mayor a cero, menor a 'durationRoof'
         * @param durationRoof mayor a cero, mayor a 'durationFloor'
         */
        public Factory(Random random, int durationFloor, int durationRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(durationFloor, "durationFloor debe ser positivo.");
            Assert.positive(durationRoof, "durationRoof debe ser positivo.");
            Assert.isTrue(durationFloor <= durationRoof, "durationFloor debe ser menor o igual a durationRoof.");

            this.random = random;
            this.durationFloor = durationFloor;
            this.durationRoof = durationRoof;
        }

        /**
         * Genera una duracion aleatoria con el rango numerico indicado por 'duracionFloor' (piso) y 'durationRoof' (techo).
         * Todas las cartas que aplican un efecto de estado, lo construyen con un randomDuration y de esta manera tenemos
         * un generador aleatorio de duraciones para los efectos de estado correspondientes a las cartas
         * @return un entero aleatorio, con el piso y techo indicados, que será la duración del efecto de estado
         */
        public int getRandomDuration() {
            return random.nextInt(durationFloor, durationRoof);
        }
    }
}
