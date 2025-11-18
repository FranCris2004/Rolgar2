package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.InvisibilityCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record InvisibilityCardConfig(
        int durationFloor,
        int durationRoof
) implements CardConfig<InvisibilityCard> {
    public InvisibilityCardConfig {
        Assert.positive(durationFloor, "durationFloor");
        Assert.isTrue(durationRoof > durationFloor, "durationRoof debe ser mayor a durationFloor");
    }

    @Override @JsonIgnore
    public Card.Factory<InvisibilityCard> getFactory(Random random) {
        return new InvisibilityCard.Factory(random, durationFloor, durationRoof);
    }
}
