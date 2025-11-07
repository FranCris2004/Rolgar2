package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

import java.util.Random;

public class InvisibilityCard extends CardWithStatusEffect {
    public InvisibilityCard(int remainingTurns) {
        super(remainingTurns);
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new InvisibilityEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<InvisibilityCard> {
        public Factory(Random random, int minRemainingTurns, int maxRemainingTurns) {
            super(random, minRemainingTurns, maxRemainingTurns);
        }

        @Override
        public InvisibilityCard create() {
            return new InvisibilityCard(getRandomRemainingTurns());
        }
    }
}
