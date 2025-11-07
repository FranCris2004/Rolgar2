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
        private final int damageFloor;
        private final int damageRoof;

        public Factory(Random random, int damageFloor, int damageRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(damageFloor, "damageFloor debe ser positivo.");
            Assert.positive(damageRoof, "damageRoof debe ser positivo.");
            Assert.isTrue(damageFloor <= damageRoof, "damageFloor debe ser menor o igual a damageRoof.");

            this.random = random;
            this.damageFloor = damageFloor;
            this.damageRoof = damageRoof;
        }

        @Override
        public FireballCard create() {
            return new FireballCard(random.nextInt(damageFloor, damageRoof));
        }
    }
}
