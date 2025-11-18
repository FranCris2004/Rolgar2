package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.TeleportCard;

import java.util.Random;

public record TeleportCardConfig() implements CardConfig<TeleportCard> {
    @Override @JsonIgnore
    public Card.Factory<TeleportCard> getFactory(Random random) {
        return new TeleportCard.Factory();
    }
}
