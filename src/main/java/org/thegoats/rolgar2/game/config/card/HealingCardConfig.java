package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.HealingCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record HealingCardConfig(
        int healingPointsFloor,
        int healingPointsRoof
) implements CardConfig<HealingCard> {
    public HealingCardConfig {
        Assert.positive(healingPointsFloor, "healingPointsFloor");
        Assert.isTrue(healingPointsRoof > healingPointsFloor, "healingPointsRoof debe ser mayor a healingPointsFloor");
    }

    @Override @JsonIgnore
    public Card.Factory<HealingCard> getFactory(Random random) {
        return new HealingCard.Factory(random, healingPointsFloor, healingPointsRoof);
    }
}
