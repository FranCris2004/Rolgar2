package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleStrengthEffect;

public class StrengthCard extends CardWithStatusEffect {
    @Override
    public void use() {
        validateTarget();
        validateRemainingTurns();
        getTarget().applyEffect(new DoubleStrengthEffect(getTarget(), getRemainingTurns()));
    }
}
