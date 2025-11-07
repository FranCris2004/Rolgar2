package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.FreezeEffect;

import java.util.Random;

public class IceballCard extends CardWithStatusEffect {
    public IceballCard(int remainingTurns) {
        super(remainingTurns);
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new FreezeEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory extends CardWithStatusEffect.Factory<IceballCard> {
        public Factory(Random random, int remainingTurnsFloor, int remainingTurnsRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
        }

        @Override
        public IceballCard create() {
            return new IceballCard(getRandomRemainingTurns());
        }
    }
}
