package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleStrengthEffect;

import java.util.Random;

public class StrengthCard extends CardWithStatusEffect {
    public StrengthCard(int remainingTurns) {
        super(remainingTurns);
    }

    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new DoubleStrengthEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<StrengthCard> {
        public Factory(Random random, int remainingTurnsFloor, int remainingTurnsRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
        }

        @Override
        public StrengthCard create() {
            return new StrengthCard(getRandomRemainingTurns());
        }
    }
}
