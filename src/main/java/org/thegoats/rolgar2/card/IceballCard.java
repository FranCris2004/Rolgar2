package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.FreezeEffect;

public class IceballCard extends CardWithStatusEffect {
    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateTarget();
        validateRemainingTurns();
        getTarget().applyEffect(new FreezeEffect(getTarget(), getRemainingTurns()));
    }

    public static class Factory implements CardFactory<IceballCard> {
        @Override
        public IceballCard create() {
            return new IceballCard();
        }
    }
}
