package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.StrengthCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record StrengthCardConfig(
        int durationFloor,
        int durationRoof
) implements CardConfig<StrengthCard> {
    public StrengthCardConfig {
        Assert.positive(durationFloor, "durationFloor");
        Assert.isTrue(durationRoof > durationFloor, "durationRoof debe ser mayor a durationFloor");
    }

    @Override @JsonIgnore
    public Card.Factory<StrengthCard> getFactory(Random random) {
        return new StrengthCard.Factory(random, durationFloor, durationRoof);
    }
}
