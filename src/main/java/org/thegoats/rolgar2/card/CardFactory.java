package org.thegoats.rolgar2.card;

/**
 * Abstract Factory de la clase Card
 * @see <a href="https://refactoring.guru/es/design-patterns/abstract-factory">Abstract Factory</a>
 */
public interface CardFactory<T extends Card> {
    T create();
}
