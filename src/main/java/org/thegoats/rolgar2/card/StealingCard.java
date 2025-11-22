package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

/**
 * Carta de robo de carta: permite al jugador robarle una carta a otro jugador de la partida
 */
public class StealingCard implements Card {
    private CardDeck thiefDeck = null;
    private CardDeck stolenDeck = null;
    private Card stolenCard = null;

    /**
     * Dado un mazo, si es valido lo setea como 'mazo del ladron'
     * @param thiefDeck no null, mazo del jugador 'ladron'
     */
    public void setThiefDeck(CardDeck thiefDeck) {
        Assert.notNull(thiefDeck, "el mazo ladron no puede ser null");
        this.thiefDeck = thiefDeck;
    }

    /**
     * Dado un mazo, si es valido lo setea como 'mazo de la víctima'
     * @param stolenDeck no null, mazo del jugador 'victima'
     */
    public void setStolenDeck(CardDeck stolenDeck) {
        Assert.notNull(stolenDeck, "El mazo a robar no puede ser null");
        this.stolenDeck = stolenDeck;
    }

    /**
     * Dada una carta válida, la setea como carta a robar
     * @param stolenCard no null, carta a robar del mazo victima
     */
    public void setStolenCard(Card stolenCard) {
        Assert.notNull(stolenCard, "La carta a robar no puede ser null");
        this.stolenCard = stolenCard;
    }

    /**
     * Chequea que los dos mazos y la carta llegado este punto sean distintos de null
     * Chequea que los mazos no sean la misma referencia
     * Chequea que la carta esté en el mazo de la víctima
     * Chequea que el mazo del ladron tenga espacio para una carta más
     * Agrega la carta al mazo del ladron
     * Remueve la carta del mazo de la victima
     */
    @Override
    public void use() {
        Assert.notNull(thiefDeck, "thiefDeck no ha sido setteado");
        Assert.notNull(stolenDeck, "stolenDeck no ha sido setteado");
        Assert.notNull(stolenCard, "stolenCard no ha sido setteado");
        Assert.isTrue(stolenDeck != thiefDeck, "El mazo del ladron no puede ser el mazo del hurtado");
        Assert.isTrue(stolenDeck.getCards().contains(stolenCard), "La carta a ser robada no se encuentra en el mazo del hurtado");
        Assert.isTrue(thiefDeck.getSize() != thiefDeck.getMaxSize(), "El mazo del ladron ya esta lleno");

        thiefDeck.add(stolenCard);
        stolenDeck.remove(stolenCard);
    }

    /**
     * @return Devuelve el nombre del efecto
     */
    public String getName(){
        return "Robo de carta";
    }

    /**
     * Fábrica de cartas de robo de carta, construye la fábrica de Card
     * y a partir de ahí permite utilizar el metodo create() para abstraerse de la implementación
     * y generar una carta de robo de carta
     */
    public static class Factory implements Card.Factory<StealingCard> {

        /**
         * Crea una carta de robo de carta
         * @return carta de robo de carta
         */
        @Override
        public StealingCard create() {
            return new StealingCard();
        }
    }

    /**
     *
     * @return devuelve una version en formato String de la carta
     */
    public String toString(){
        return String.format("StealingCard[thiefDeck=%s, stolenDeck=%s, stolenCard=%s]",
                thiefDeck,
                stolenDeck,
                stolenCard);}
}
