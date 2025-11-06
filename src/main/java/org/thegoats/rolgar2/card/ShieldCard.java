package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.HalfDamageEffect;
import org.thegoats.rolgar2.util.Assert;

public class ShieldCard extends CardWithStatusEffect {
    private Double incomingDamageFactorModifier = null;

    @Override
    public void use() {
        validateTarget();
        validateDuration();
        Assert.notNull(incomingDamageFactorModifier, "incomingDamageFactorModifier no ha sido setteado");
        getTarget().applyEffect(new HalfDamageEffect(getTarget(), getDuration()));
    }

    public void setIncomingDamageFactorModifier(double incomingDamageFactorModifier) {
        Assert.positive(incomingDamageFactorModifier, "incomingDamageFactorModifier debe ser positivo");
        this.incomingDamageFactorModifier = incomingDamageFactorModifier;
    }
}
