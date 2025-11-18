package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;

import java.util.Random;

public interface CardConfig<T extends Card> {
    @JsonIgnore
    Card.Factory<T> getFactory(Random random);
}
