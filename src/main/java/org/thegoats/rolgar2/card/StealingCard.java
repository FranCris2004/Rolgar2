package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

public class StealingCard implements Card {
    private CardDeck thiefDeck = null;
    private CardDeck stolenDeck = null;
    private Card stolenCard = null;

    private void setThiefInventory(CardDeck thiefDeck) {
        this.thiefDeck = thiefDeck;
    }

    private void setStolenDeck(CardDeck stolenDeck) {
        this.stolenDeck = stolenDeck;
    }

    private void setStolenCard(Card stolenCard) {
        this.stolenCard = stolenCard;
    }

    @Override
    public void use() {
        Assert.notNull(thiefDeck, "thiefDeck no ha sido setteado");
        Assert.notNull(stolenDeck, "stolenDeck no ha sido setteado");
        Assert.notNull(stolenCard, "stolenCard no ha sido setteado");
        Assert.isTrue(stolenDeck != thiefDeck, "El mazo del ladron no puede ser el mazo del hurtado");
        Assert.isTrue(thiefDeck.getCards().contains(stolenCard), "La carta a ser robada no se encuentra en el mazo del hurtado");

        thiefDeck.add(stolenCard);
        stolenDeck.remove(stolenCard);
    }
}
