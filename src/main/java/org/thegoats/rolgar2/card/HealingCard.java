package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 * Carta que cura al personaje 'target' en 'healingPoints' unidades
 */
public class HealingCard extends CardWithCharacterTarget {
    private final int healingPoints;

    public HealingCard(int healingPoints) {
        Assert.positive(healingPoints, "healingPoints debe ser positiva.");
        this.healingPoints = healingPoints;
    }

    /**
     * Chequea que a la carta ya se le haya asignado un personaje 'target' objetivo, y que se le haya setteado unos
     * healingPoints validos.
     * Si ambas condiciones se cumplen, daña a 'target' en funcion de damage
     */
    @Override
    public void use() {
        validateTarget();
        Assert.notNull(healingPoints, "healingPoints no fue setteado");
        getTarget().recoverHealth(healingPoints);
    }

    /**
     * Fabrica de cartas de curación, construye la fabrica de
     * CardWithStatusEffect con un generador aleatorio, piso y techo de duraciones,
     * y a partir de ahí permite utilizar el metodo create() para abstraerse de la implementacion
     * y generar una carta con curacion aleatoria
     */
    public static class Factory implements Card.Factory<HealingCard> {
        private final Random random;
        private final int healingPointsFloor;
        private final int healingPointsRoof;

        /**
         * Construye la fabrica de cartas de curacion
         * @param random no null, objeto generador aleatorio
         * @param healingPointsFloor mayor a cero, menor a 'healingPointsRoof'
         * @param healingPointsRoof mayor a cero, mayor a 'healingPointsFloor'
         */
        public Factory(Random random, int healingPointsFloor, int healingPointsRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(healingPointsFloor, "healingPointsFloor debe ser positivo.");
            Assert.positive(healingPointsRoof, "healingPointsRoof debe ser positivo.");
            Assert.isTrue(healingPointsFloor <= healingPointsRoof, "healingPointsFloor debe ser menor o igual a healingPointsRoof.");

            this.random = random;
            this.healingPointsFloor = healingPointsFloor;
            this.healingPointsRoof = healingPointsRoof;
        }

        /**
         * Crea una carta de curacion con puntos de vida aleatorios entre 'healingPointsFloor' y 'healingPointsRoof'
         * pasados por parametro al constructor de Factory
         * @return carta de curacion
         */
        @Override
        public HealingCard create() {
            return new HealingCard(random.nextInt(healingPointsFloor, healingPointsRoof));
        }
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    public String toString(){
        return String.format("HealingCard[target=%s, healingPoints=%d]",
                getTarget().toString(),
                healingPoints);
    }
}
