package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 * Muchas de las cartas que tienen un CharacterData como target, le aplican un efecto de estado StatusEffect a dicho personaje,
 * por lo tanto abstraemos en esta clase el atributo remainingTurns, que sera la duracion del efecto de estado en turnos,
 * y los metodos setRemainingTurns, getRemainingTurns, y validateRemainingTurns. Todas las cartas que apliquen un efecto
 * de estado heredaran de esta clase.
 */
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
        protected final int remainingTurnsFloor;
        protected final int remainingTurnsRoof;

        public Factory(Random random, int remainingTurnsFloor, int remainingTurnsRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(remainingTurnsFloor, "remainingTurnsFloor debe ser positivo.");
            Assert.positive(remainingTurnsRoof, "remainingTurnsRoof debe ser positivo.");
            Assert.isTrue(remainingTurnsFloor <= remainingTurnsRoof, "remainingTurnsFloor debe ser menor o igual a remainingTurnsRoof.");

            this.random = random;
            this.remainingTurnsFloor = remainingTurnsFloor;
            this.remainingTurnsRoof = remainingTurnsRoof;
        }

        public int getRandomRemainingTurns() {
            return random.nextInt(remainingTurnsFloor, remainingTurnsRoof);
        }
    }
}
