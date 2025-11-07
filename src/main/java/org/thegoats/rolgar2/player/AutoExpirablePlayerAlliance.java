package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

public class AutoExpirablePlayerAlliance extends PlayerAlliance {
    private int remainingTurns;

    public AutoExpirablePlayerAlliance(int remainingTurns) {
        Assert.positive(remainingTurns, "remainingTurns debe ser mayor o igual a cero");
        this.remainingTurns = remainingTurns;
    }

    public void updateRemainingTurns() {
        this.remainingTurns--;
    }

    @Override
    public boolean inForce() {
        return super.inForce() || this.remainingTurns <= 0;
    }
}
