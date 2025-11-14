package org.thegoats.rolgar2.card;
import org.thegoats.rolgar2.util.Assert;
import java.util.LinkedList;
import java.util.List;

/**
 * Mazo de cartas que tendran los jugadores o inventario
 */

public class CardDeck {
    private final List<Card> cards;
    private int maxSize;

    /**
     * Crea el mazo de cartas con un tope de 'size' cartas
     * @param size mayor a cero
     */
    public CardDeck(int size) {
        this.cards = new LinkedList<>();
        setMaxSize(size);
    }


    /**
     *
     * @param card no null
     */
    public void add(Card card) {
        Assert.notNull(card, "La carta a agregar no puede ser null");
        if (cards.size() >= maxSize) {
            throw new IllegalStateException("El mazo ya esta lleno");
        }

        cards.add(card);
    }

    /**
     * Setea el tope de cartas del mazo
     * @param maxSize mayor a cero
     */
    public void setMaxSize(int maxSize){
        Assert.positive(maxSize, "El tamaño del inventario debe ser positivo");
        this.maxSize = maxSize;
    }

    /**
     * Remueve una carta dada del mazo, esto se resuelve segun el equals de card
     * @param card no null, carta a remover del mazo
     */
    public void remove(Card card) {
        Assert.notNull(card, "la carta a buscar no puede ser null");
        cards.remove(card);
    }

    /**
     * Devuelve una lista de las cartas, no modificable, para solo lectura
     * @return lista inmutable de las cartas
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }


    /**
     * Devuelve la cantidad de cartas que posee el mazo
     * @return cantidad actual de cartas
     */
    public int getSize() {
        return cards.size();
    }

    /**
     *
     * @return maximo tamaño que puede tomar el mazo
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * @return true si el mazo ya esta lleno
     */
    public boolean isFull(){
        return maxSize == cards.size();
    }

}
