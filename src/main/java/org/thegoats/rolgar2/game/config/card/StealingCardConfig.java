package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.StealingCard;

import java.util.Random;

public record StealingCardConfig() implements CardConfig<StealingCard> {
    @Override @JsonIgnore
    public Card.Factory<StealingCard> getFactory(Random random) {
        return new StealingCard.Factory();
    }
}
