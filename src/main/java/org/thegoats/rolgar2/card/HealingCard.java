package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public class HealingCard extends CardWithCharacterTarget {
    private final int healingPoints;

    public HealingCard(int healingPoints) {
        Assert.positive(healingPoints, "healingPoints debe ser positiva.");
        this.healingPoints = healingPoints;
    }

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
}
