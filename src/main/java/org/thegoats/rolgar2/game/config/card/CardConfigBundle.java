package org.thegoats.rolgar2.game.config.card;

import org.thegoats.rolgar2.util.Assert;

public record CardConfigBundle(
        DoubleMoveCardConfig doubleMoveCardConfig,
        FireballCardConfig fireballCardConfig,
        HealingCardConfig healingCardConfig,
        IceballCardConfig iceballCardConfig,
        InvisibilityCardConfig invisibilityCardConfig,
        ShieldCardConfig shieldCardConfig,
        StealingCardConfig stealingCardConfig,
        StrengthCardConfig strengthCardConfig,
        TeleportCardConfig teleportCardConfig
) {
    public CardConfigBundle {
        Assert.notNull(doubleMoveCardConfig, "doubleMoveCardConfig");
        Assert.notNull(fireballCardConfig, "fireballCardConfig");
        Assert.notNull(healingCardConfig, "healingCardConfig");
        Assert.notNull(iceballCardConfig, "iceballCardConfig");
        Assert.notNull(invisibilityCardConfig, "invisibilityCardConfig");
        Assert.notNull(shieldCardConfig, "shieldCardConfig");
        Assert.notNull(stealingCardConfig, "stealingCardConfig");
        Assert.notNull(strengthCardConfig, "strengthCardConfig");
        Assert.notNull(teleportCardConfig, "teleportCardConfig");
    }
}
