package org.thegoats.rolgar2.card;

import java.util.LinkedList;
import java.util.List;

public class CardDeck {
    private final List<Card> cards;
    private final int maxSize;

    public CardDeck(int size) {
        this.cards = new LinkedList<>();
        this.maxSize = size;
    }

    public int size() {
        return cards.size();
    }

    public int maxSize() {
        return maxSize;
    }

    public void add(Card card) {
        if (cards.size() >= maxSize) {
            throw new IllegalStateException("El mazo ya esta lleno");
        }

        cards.add(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }
}
