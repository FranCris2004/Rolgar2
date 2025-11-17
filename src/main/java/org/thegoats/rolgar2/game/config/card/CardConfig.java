package org.thegoats.rolgar2.game.config.card;

import org.thegoats.rolgar2.card.Card;

import java.util.Random;

public interface CardConfig<T extends Card> {
    Card.Factory<T> getFactory(Random random);
}
