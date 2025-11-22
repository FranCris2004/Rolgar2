package org.thegoats.rolgar2.card;
import org.thegoats.rolgar2.character.effects.HalfDamageEffect;
import java.util.Random;

/**
 * Carta de escudo: al aplicarse sobre el personaje 'target' reduce su daño recibido al 50%
 */
public class ShieldCard extends CardWithStatusEffect {

    /**
     * Construye la carta de escudo
     * @param duration mayor a cero
     */
    public ShieldCard(int duration) {
        super(duration);
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, 'target' no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new HalfDamageEffect(getTarget(), getDuration()));
    }

    public String getName(){
        return "Escudo";
    }

    /**
     * Fábrica de cartas de escudo, construye la fábrica de
     * CardWithStatusEffect con un generador aleatorio, piso y techo de duraciones,
     * y a partir de ahí permite utilizar el metodo create() para abstraerse de la implementación
     * y generar una carta cuyo efecto de estado a aplicar tenga duración aleatoria
     */
    public static class Factory extends CardWithStatusEffect.Factory<ShieldCard> {

         /**
         * Construye la fábrica de cartas de escudo
         * @param random no null, objeto generador aleatorio
         * @param durationFloor mayor a cero, menor a 'durationRoof'
         * @param durationRoof mayor a cero, mayor a 'durationFloor'
         */
        public Factory(Random random, int durationFloor, int durationRoof) {
            super(random, durationFloor, durationRoof);
        }

        /**
         * Crea una carta de escudo cuyo efecto de estado tiene una duración aleatoria entre
         * 'durationFloor' y 'durationRoof' pasados por parámetro al constructor de Factory
         * @return carta de escudo
         */
        @Override
        public ShieldCard create() {
            return new ShieldCard(getRandomDuration());
        }
    }
    /**
     * Devuelve la carta en formato string
     * @return formato en String de la carta, con el formato:
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN
     */
    @Override
    public String toString(){
        return String.format("ShieldCard[target=%s, Duration=%d]",
                getTarget(),
                getDuration());
    }

}
