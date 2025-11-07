package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.HalfDamageEffect;
import org.thegoats.rolgar2.util.Assert;
import java.util.Random;


public class ShieldCard extends CardWithStatusEffect {
    private final double incomingDamageFactorModifier;

    public ShieldCard(int remainingTurns, double incomingDamageFactorModifier) {
        super(remainingTurns);
        Assert.positive(incomingDamageFactorModifier, "incomingDamageFactorModifier debe ser positivo");
        this.incomingDamageFactorModifier = incomingDamageFactorModifier;
    }

    @Override
    public void use() {
        validateTarget();
        Assert.notNull(incomingDamageFactorModifier, "incomingDamageFactorModifier no ha sido setteado");
        getTarget().applyEffect(new HalfDamageEffect(getTarget(), getDuration()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<ShieldCard> {
        private final double incomingDamageFactorModifierFloor;
        private final double incomingDamageFactorModifierRoof;

        public Factory(Random random,
                       int remainingTurnsFloor,
                       int remainingTurnsRoof,
                       double incomingDamageFactorModifierFloor,
                       double incomingDamageFactorModifierRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
            Assert.positive(incomingDamageFactorModifierFloor, "incomingDamageFactorModifierFloor debe ser positivo");
            Assert.positive(incomingDamageFactorModifierRoof, "incomingDamageFactorModifierRoof debe ser positivo");
            Assert.isTrue(incomingDamageFactorModifierFloor <= incomingDamageFactorModifierRoof,
                    "incomingDamageFactorModifierFloor debe ser menor o igual a incomingDamageFactorModifierRoof");
            this.incomingDamageFactorModifierFloor = incomingDamageFactorModifierFloor;
            this.incomingDamageFactorModifierRoof = incomingDamageFactorModifierRoof;
        }

        @Override
        public ShieldCard create() {
            return new ShieldCard(getRandomDuration(),
                    random.nextDouble(incomingDamageFactorModifierFloor, incomingDamageFactorModifierRoof));
        }
    }
}
