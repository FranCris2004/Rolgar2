package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.DoubleMoveCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record DoubleMoveCardConfig (
        int durationFloor,
        int durationRoof
) implements CardConfig<DoubleMoveCard> {
    public DoubleMoveCardConfig {
        Assert.positive(durationFloor, "durationFloor");
        Assert.isTrue(durationRoof > durationFloor, "durationRoof debe ser mayor a durationFloor");
    }

    @Override @JsonIgnore
    public DoubleMoveCard.Factory getFactory(Random random) {
        return new DoubleMoveCard.Factory(random, durationFloor, durationRoof);
    }
}
