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
        getTarget().applyEffect(new HalfDamageEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<ShieldCard> {
        private final double minIncomingDamageFactorModifier;
        private final double maxIncomingDamageFactorModifier;

        public Factory(Random random,
                       int minRemainingTurns,
                       int maxRemainingTurns,
                       double minIncomingDamageFactorModifier,
                       double maxIncomingDamageFactorModifier) {
            super(random, minRemainingTurns, maxRemainingTurns);
            Assert.positive(minIncomingDamageFactorModifier, "minIncomingDamageFactorModifier debe ser positivo");
            Assert.positive(maxIncomingDamageFactorModifier, "maxIncomingDamageFactorModifier debe ser positivo");
            Assert.isTrue(minIncomingDamageFactorModifier <= maxIncomingDamageFactorModifier,
                    "minIncomingDamageFactorModifier debe ser menor o igual a maxIncomingDamageFactorModifier");
            this.minIncomingDamageFactorModifier = minIncomingDamageFactorModifier;
            this.maxIncomingDamageFactorModifier = maxIncomingDamageFactorModifier;
        }

        @Override
        public ShieldCard create() {
            return new ShieldCard(getRandomRemainingTurns(),
                    random.nextDouble(minIncomingDamageFactorModifier, maxIncomingDamageFactorModifier));
        }
    }
}
