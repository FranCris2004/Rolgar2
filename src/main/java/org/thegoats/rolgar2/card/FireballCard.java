package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public class FireballCard extends CardWithCharacterTarget {
    private final int damage;

    public FireballCard(int damage) {
        Assert.positive(damage, "damage debe ser positivo.");
        this.damage = damage;
    }

    @Override
    public void use() {
        validateTarget();
        getTarget().takeDamage(damage);
    }

    public static class Factory implements Card.Factory<FireballCard> {
        private final Random random;
        private final int minDamage;
        private final int maxDamage;

        public Factory(Random random, int minDamage, int maxDamage) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(minDamage, "minDamage debe ser positivo.");
            Assert.positive(maxDamage, "maxDamage debe ser positivo.");
            Assert.isTrue(minDamage <= maxDamage, "minDamage debe ser menor o igual a maxDamage.");

            this.random = random;
            this.minDamage = minDamage;
            this.maxDamage = maxDamage;
        }

        @Override
        public FireballCard create() {
            return new FireballCard(random.nextInt(minDamage, maxDamage + 1));
        }
    }
}
