package org.thegoats.rolgar2.card;

import java.util.List;

public class CardDeck {
    private List<Card> cards;

    public CardDeck() {}

    public void add(Card card) {
        cards.add(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
