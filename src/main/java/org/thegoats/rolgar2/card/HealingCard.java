package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

public class HealingCard extends CardWithCharacterTarget {
    private Integer healingPoints = null;

    @Override
    public void use() {
        validateTarget();
        Assert.notNull(healingPoints, "healingPoints no fue setteado");
        getTarget().recoverHealth(healingPoints);
    }

    public void setHealingPoints(int healingPoints) {
        Assert.positive(healingPoints, "healingPoints debe ser positiva.");
        this.healingPoints = healingPoints;
    }
}
