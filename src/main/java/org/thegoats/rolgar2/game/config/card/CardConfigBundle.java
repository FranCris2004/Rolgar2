package org.thegoats.rolgar2.game.config.card;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.structures.lists.LinkedList;

import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public Set<Card.Factory<? extends Card>> getFactories(Random random) {
        var factories = new org.thegoats.rolgar2.util.structures.sets.Set<Card.Factory<? extends Card>>();

        factories.add(doubleMoveCardConfig.getFactory(random));
        factories.add(fireballCardConfig.getFactory(random));
        factories.add(healingCardConfig.getFactory(random));
        factories.add(iceballCardConfig.getFactory(random));
        factories.add(invisibilityCardConfig.getFactory(random));
        factories.add(shieldCardConfig.getFactory(random));
        factories.add(stealingCardConfig.getFactory(random));
        factories.add(strengthCardConfig.getFactory(random));
        factories.add(teleportCardConfig.getFactory(random));

        return factories;
    }
}
