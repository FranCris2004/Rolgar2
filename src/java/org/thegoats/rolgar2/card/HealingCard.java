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

    public static class Factory implements Card.Factory<HealingCard> {
        private final Random random;
        private final int healingPointsFloor;
        private final int healingPointsRoof;

        public Factory(Random random, int healingPointsFloor, int healingPointsRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(healingPointsFloor, "healingPointsFloor debe ser positivo.");
            Assert.positive(healingPointsRoof, "healingPointsRoof debe ser positivo.");
            Assert.isTrue(healingPointsFloor <= healingPointsRoof, "healingPointsFloor debe ser menor o igual a healingPointsRoof.");

            this.random = random;
            this.healingPointsFloor = healingPointsFloor;
            this.healingPointsRoof = healingPointsRoof;
        }

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
