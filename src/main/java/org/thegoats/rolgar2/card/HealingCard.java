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

    public static class Factory implements CardFactory<HealingCard> {
        private final Random random;
        private final int minHealingPoints;
        private final int maxHealingPoints;

        public Factory(Random random, int minHealingPoints, int maxHealingPoints) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(minHealingPoints, "minHealingPoints debe ser positivo.");
            Assert.positive(maxHealingPoints, "maxHealingPoints debe ser positivo.");
            Assert.isTrue(minHealingPoints <= maxHealingPoints, "minHealingPoints debe ser menor o igual a maxHealingPoints.");

            this.random = random;
            this.minHealingPoints = minHealingPoints;
            this.maxHealingPoints = maxHealingPoints;
        }

        @Override
        public HealingCard create() {
            return new HealingCard(random.nextInt(minHealingPoints, maxHealingPoints + 1));
        }
    }
}
