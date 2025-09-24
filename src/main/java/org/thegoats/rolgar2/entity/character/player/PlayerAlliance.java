package org.thegoats.rolgar2.entity.character.player;

import org.thegoats.rolgar2.util.Assert;

public final class PlayerAlliance {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private int remainingTurns;

    public PlayerAlliance(Player firstPlayer, Player secondPlayer, int remainingTurns) {
        Assert.notNull(firstPlayer, "firstPlayer must not be null");
        Assert.notNull(secondPlayer, "secondPlayer must not be null");
        Assert.positive(remainingTurns, "remainingTurns must not be negative");
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.remainingTurns = remainingTurns;
    }

    public void passTurn() {
        remainingTurns = Math.max(remainingTurns - 1, 0);
    }

    public void cancel() {

    }

    public boolean stillInForce() {
        return remainingTurns > 0;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
