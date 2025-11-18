package org.thegoats.rolgar2.game.config.card;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.IceballCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record IceballCardConfig(
        int durationFloor,
        int durationRoof
) implements CardConfig<IceballCard> {
    public IceballCardConfig {
        Assert.positive(durationFloor, "durationFloor");
        Assert.isTrue(durationRoof > durationFloor, "durationRoof debe ser mayor a durationFloor");
    }

    @Override
    public Card.Factory<IceballCard> getFactory(Random random) {
        return new IceballCard.Factory(random, durationFloor, durationRoof);
    }
}
