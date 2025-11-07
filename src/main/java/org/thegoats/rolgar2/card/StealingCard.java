package org.thegoats.rolgar2.card;

public class StealingCard extends CardWithCharacterTarget {
    // TODO: Implementar

    @Override
    public void use() {
        validateTarget();
    }

    public static class Factory implements Card.Factory<StealingCard> {
        @Override
        public StealingCard create() {
            return new StealingCard();
        }
    }
}
