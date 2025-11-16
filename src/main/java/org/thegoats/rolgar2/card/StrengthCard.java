package org.thegoats.rolgar2.card;
import org.thegoats.rolgar2.character.effects.DoubleStrengthEffect;
import java.util.Random;

/**
 * Carta de fuerza: carta que al usarse duplica el daño del personaje
 */

public class StrengthCard extends CardWithStatusEffect {
    /**
     * Construye la carta de fuerza
     * @param duration mayor a cero
     */
    public StrengthCard(int duration) {
        super(duration);
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, 'target' no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new DoubleStrengthEffect(getTarget(), getDuration()));
    }

    /**
     * Fábrica de cartas de fuerza, construye la fábrica de
     * CardWithStatusEffect con un generador aleatorio, piso y techo de duraciones,
     * y a partir de ahí permite utilizar el metodo create() para abstraerse de la implementación
     * y generar una carta cuyo efecto de estado a aplicar tenga duración aleatoria
     */
    public static class Factory extends CardWithStatusEffect.Factory<StrengthCard> {

        /**
         * Construye la fábrica de cartas de fuerza
         * @param random no null, objeto generador aleatorio
         * @param durationFloor mayor a cero, menor a 'durationRoof'
         * @param durationRoof mayor a cero, mayor a 'durationFloor'
         */
        public Factory(Random random, int durationFloor, int durationRoof) {
            super(random, durationFloor, durationRoof);
        }

        /**
         * Crea una carta de fuerza cuyo efecto de estado tiene una duración aleatoria entre
         * 'durationFloor' y 'durationRoof' pasados por parámetro al constructor de Factory
         * @return carta de fuerza
         */
        @Override
        public StrengthCard create() {
            return new StrengthCard(getRandomDuration());
        }
    }
}
