package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WorldCell {
    //
    // Atributos privados
    //

    private Floor floor;
    private Wall wall;
    private CharacterData character;
    private Card card;

    private final Position position;
    private List<WorldCell> neighbors = null;

    //
    // Constructores
    //

    public WorldCell(Position position) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        this.position = position;
    }

    //
    // Inicializacion
    //

    public void initNeighbors(List<WorldCell> neighbors) {
        Assert.notNull(neighbors, "'neighbors' debe ser no nulo.");

        if (this.neighbors != null) {
            throw new IllegalArgumentException("Ya se ha inicializado la lista de vecinos");
        }

        this.neighbors = List.copyOf(neighbors); // copia ininmutable
    }

    //
    // Getters
    //

    public Optional<Floor> getFloor() {
        return Optional.ofNullable(floor);
    }

    public Optional<Wall> getWall() {
        return Optional.ofNullable(wall);
    }

    public Optional<CharacterData> getCharacter() {
        return Optional.ofNullable(character);
    }

    public Optional<Card> getCard() {
        return Optional.ofNullable(card);
    }

    public Position getPosition() {
        return position;
    }

    public List<WorldCell> getNeighbors() {
        return List.copyOf(neighbors);
    }

    //
    // Setters
    //

    public void clear() {
        floor = null;
        wall = null;
        character = null;
        card = null;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public void setWall(Wall wall) {
        if (wall == null) {
            this.wall = null;
        } else {
            if (character != null) {
                throw new IllegalStateException("La celda contiene un personaje actualmente");
            }
            if (card != null) {
                throw new IllegalStateException("La celda contiene una carta actualmente");
            }

            this.wall = wall;
        }
    }

    public void setCharacter(CharacterData character) {
        if (character == null) {
            this.character = null;
        } else {
            if (wall != null) {
                throw new IllegalStateException("La celda contiene un muro actualmente");
            }
            if (card != null) {
                throw new IllegalStateException("La celda contiene una carta actualmente");
            }

    public boolean hasCharacter()
    {
        return !hasNull() && content instanceof CharacterData;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean hasFloor() {
        return floor != null;
    }

    public boolean isOccupied() {
        return wall != null || character != null || card != null;
    }

    public boolean isWalkable() {
        return hasFloor() && !isOccupied() && floor.isWalkable();
    }

    //
    // Implementacion de Object
    //

    @Override
    public String toString() {
        // ADVERTENCIA: No imprimir los vecinos, ya que se generaria una impresion recursiva,
        // cada vecino imprimira sus vecinos, en donde también entra este y asi sucesivamente
        return String.format(
                "WorldCell[position=%s, floor=%s, wall=%s, character=%s, card=%s]",
                position, floor, wall, character, card
        );
    }

    /**
     * @param o objeto a comparar
     * @return true si this == o, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        // Ya que WorldCell fue diseñado para ser de unica referencia en el tablero,
        // cada instancia es igual únicamente a sí misma
        return this == o;
    }

    /**
     * @return Hash code con base en la posicion de la celda
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
