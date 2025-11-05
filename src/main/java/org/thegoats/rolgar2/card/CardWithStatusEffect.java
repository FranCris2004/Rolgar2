package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

public abstract class CardWithStatusEffect extends CardWithCharacterTarget {
    private int remainingTurns = -1;

    public void setRemainingTurns(int remainingTurns) {
        Assert.positive(remainingTurns, "remainingTurns debe ser positivo.");
        this.remainingTurns = remainingTurns;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void validateRemainingTurns() {
        Assert.positive(remainingTurns, "remainingTurns no ha sido setteado.");
    }
}
