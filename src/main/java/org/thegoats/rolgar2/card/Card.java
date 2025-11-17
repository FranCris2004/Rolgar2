package org.thegoats.rolgar2.card;

/**
 * Interfaz que contiene el comportamiento general de todas las cartas
 */
public interface Card {
    /**
     * Ejecuta la accion de la carta
     */
    void use();

    String getName();

    /**
     * Abstract Factory de la clase Card
     * @see <a href="https://refactoring.guru/es/design-patterns/abstract-factory">Abstract Factory</a>
     */
    interface Factory<T extends Card> {
        T create();
    }
}
