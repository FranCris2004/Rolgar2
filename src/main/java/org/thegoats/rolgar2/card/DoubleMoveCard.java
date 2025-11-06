package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleMoveEffect;

public class DoubleMoveCard extends CardWithStatusEffect {
    @Override
    public void use() {
        validateTarget();
        validateRemainingTurns();
        getTarget().applyEffect(new DoubleMoveEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory implements CardFactory<DoubleMoveCard> {
        @Override
        public DoubleMoveCard create() {
            return new DoubleMoveCard();
        }
    }
}
