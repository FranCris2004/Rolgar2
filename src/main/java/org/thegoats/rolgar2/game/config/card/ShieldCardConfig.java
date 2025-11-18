package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.ShieldCard;

import java.util.Random;

public record ShieldCardConfig(
        int durationFloor,
        int durationRoof
) implements CardConfig<ShieldCard> {
    @Override @JsonIgnore
    public Card.Factory<ShieldCard> getFactory(Random random) {
        return new ShieldCard.Factory(random, durationFloor, durationRoof);
    }
}
