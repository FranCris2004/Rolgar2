package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public abstract class CardWithStatusEffect extends CardWithCharacterTarget {
    private final int remainingTurns;

    public CardWithStatusEffect(int remainingTurns) {
        Assert.positive(remainingTurns, "remainingTurns debe ser positivo.");
        this.remainingTurns = remainingTurns;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public static abstract class Factory<T extends CardWithStatusEffect> implements Card.Factory<T> {
        protected final Random random;
        protected final int minRemainingTurns;
        protected final int maxRemainingTurns;

        public Factory(Random random, int minRemainingTurns, int maxRemainingTurns) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(minRemainingTurns, "minRemainingTurns debe ser positivo.");
            Assert.positive(maxRemainingTurns, "maxRemainingTurns debe ser positivo.");
            Assert.isTrue(minRemainingTurns <= maxRemainingTurns, "minRemainingTurns debe ser menor o igual a maxRemainingTurns.");

            this.random = random;
            this.minRemainingTurns = minRemainingTurns;
            this.maxRemainingTurns = maxRemainingTurns;
        }

        public int getRandomRemainingTurns() {
            return random.nextInt(minRemainingTurns, maxRemainingTurns + 1);
        }
    }
}
