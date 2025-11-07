package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleStrengthEffect;

import java.util.Random;

public class StrengthCard extends CardWithStatusEffect {
    public StrengthCard(int duration) {
        super(duration);
    }

    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new DoubleStrengthEffect(getTarget(), getDuration()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<StrengthCard> {
        public Factory(Random random, int durationFloor, int durationRoof) {
            super(random, durationFloor, durationRoof);
        }

        @Override
        public StrengthCard create() {
            return new StrengthCard(getRandomDuration());
        }
    }
}
