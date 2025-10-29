package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

public class InvisibilityCard extends CardWithStatusEffect {
    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        validateSetTarget();
        validateSetRemainingTurns();
        getTarget().applyEffect(new InvisibilityEffect(getTarget(), getRemainingTurns()));
    }
}
