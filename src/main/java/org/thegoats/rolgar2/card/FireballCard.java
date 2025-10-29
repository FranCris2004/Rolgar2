package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

public class FireballCard extends CardWithCharacterTarget {
    private int damage = -1;

    @Override
    public void use() {
        validateSetTarget();
        Assert.positive(damage, "damage no ha sido setteado.");
        getTarget().takeDamage(damage);
    }

    public void setDamage(int damage) {
        Assert.positive(damage, "damage debe ser positivo.");
        this.damage = damage;
    }
}
