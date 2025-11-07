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
        public Factory(Random random, int
                remainingTurnsFloor, int remainingTurnsRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
        }

        @Override
        public DoubleMoveCard create() {
            return new DoubleMoveCard(getRandomRemainingTurns());
        }
    }
}
