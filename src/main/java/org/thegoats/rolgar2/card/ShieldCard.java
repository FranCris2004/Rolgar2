package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.HalfDamageEffect;
import org.thegoats.rolgar2.util.Assert;

public class ShieldCard extends CardWithStatusEffect {
    private double incomingDamageFactorModifier = -1.0;

    @Override
    public void use() {
        validateSetTarget();
        validateSetRemainingTurns();
        Assert.positive(incomingDamageFactorModifier, "incomingDamageFactorModifier no ha sido setteado");
        getTarget().applyEffect(new HalfDamageEffect(getTarget(), getRemainingTurns()));
    }

    public void setIncomingDamageFactorModifier(double incomingDamageFactorModifier) {
        Assert.positive(incomingDamageFactorModifier, "incomingDamageFactorModifier debe ser positivo");
        this.incomingDamageFactorModifier = incomingDamageFactorModifier;
    }
}
