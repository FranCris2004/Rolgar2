package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleMoveEffect;

import java.util.Random;

public class DoubleMoveCard extends CardWithStatusEffect {
    public DoubleMoveCard(int remainingTurns) {
        super(remainingTurns);
    }

    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new DoubleMoveEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<DoubleMoveCard> {
        public Factory(Random random, int minRemainingTurns, int maxRemainingTurns) {
            super(random, minRemainingTurns, maxRemainingTurns);
        }

        @Override
        public DoubleMoveCard create() {
            return new DoubleMoveCard(getRandomRemainingTurns());
        }
    }
}
