package org.thegoats.rolgar2.card;
import java.util.Random;
import org.thegoats.rolgar2.character.effects.FreezeEffect;

/**
 * Carta que aplica un efecto de estado de congelamiento al 'target' indicado con 'duration' turnos.
 */

public class IceballCard extends CardWithStatusEffect {
    /**
     * Construye la carta de bola de nieve
     * @param duration mayor a cero
     */
    public IceballCard(int duration) {
        super(duration);
    }

    /**
     * Aplica el efecto de la carta sobre el 'target' indicado, con una duracion de 'duration' turnos
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new FreezeEffect(getTarget(), getDuration()));
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    @Override
    public String toString(){
        return String.format("IceballCard[target=%s, duration=%d]",
                getTarget(),
                getDuration());
    }

    /**
     * @return Devuelve el nombre del efecto
     */
    public String getName(){
        return "Bola de nieve";
    }

    /**
     * Fabrica de cartas de bola de nieve, construye la fabrica de
     * CardWithStatusEffect con un generador aleatorio, piso y techo de duraciones,
     * y a partir de ahi permite utilizar el metodo create() para abstraerse de la implementacion
     * y generar una carta cuyo efecto de estado a aplicar tenga duracion aleatoria
     */
    public static class Factory extends CardWithStatusEffect.Factory<IceballCard> {
        /**
         * Construye la fabrica de cartas de bola de nieve
         * @param random no null, objeto generador aleatorio
         * @param durationFloor mayor a cero, menor a 'durationRoof'
         * @param durationRoof mayor a cero, mayor a 'durationFloor'
         */
        public Factory(Random random, int durationFloor, int durationRoof) {
            super(random, durationFloor, durationRoof);
        }

        /**
         * Crea una carta de bola de nieve cuyo efecto de estado tiene una duracion aleatoria entre 
         * 'durationFloor' y 'durationRoof' pasados por parametro al constructor de Factory
         * @return carta de bola de nieve
         */
        @Override
        public IceballCard create() {
            return new IceballCard(getRandomDuration());
        }
    }
}
