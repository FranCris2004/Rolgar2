package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.game.GameCharacter;
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
    private Card card;
    private GameCharacter character;

    private final Position position;
    private List<WorldCell> neighbors = null;

    //
    // Constructores
    //

    /**
     * Crea una celda con posicion position
     * @param position no null, posicion de la celda en el tablero
     */
    public WorldCell(Position position) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        this.position = position;
    }

    //
    // Inicializacion
    //

    /**
     * Se inicializan los vecinos indicados en neighbors
     * @param neighbors lista de celdas vecinas
     */
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

    /**
     * @return floor si es distinto de null, Options.EMPTY si floor es null
     */
    public Optional<Floor> getFloor() {
        return Optional.ofNullable(floor);
    }

    /**
     * @return wall si es distinto de null, Options.EMPTY si wall es null
     */
    public Optional<Wall> getWall() {
        return Optional.ofNullable(wall);
    }

    /**
     * @return GameCharacter si character es distinto de null, Options.EMPTY si character es null
     */
    public Optional<GameCharacter> getCharacter() {
        return Optional.ofNullable(character);
    }

    /**
     * @return Card si card es distinto de null, Options.EMPTY si card es null
     */
    public Optional<Card> getCard() {
        return Optional.ofNullable(card);
    }

    /**
     * Obtiene la posicion de la celda en el mundo
     * @return Posicion de la celda
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Devuelve una copia de las celdas vecinas
     * @return Lista copiada de las celdas vecinas
     */
    public List<WorldCell> getNeighbors() {
        return List.copyOf(neighbors);
    }

    public Optional<WorldCell> getUpperNeighbor(){
        return getNeighbors().stream()
                .filter(neighbor -> neighbor.position.getLayer() == position.getLayer() +1)
                .findFirst();
    }

    //
    // Setters
    //

    /**
     * Vacía la celda totalmente
     */
    public void clear() {
        floor = null;
        wall = null;
        character = null;
        card = null;
    }

    /**
     * @param floor se permite que sea null. No se hace ninguna validacion porque puede haber piso y otra cosa
     * simultáneamente
     */
    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    /**
     * @param wall puede ser null. Lanza una excepcion si ya habia un character o card
     */
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

    /**
     * @param character null o character. Lanza una excepcion si ya habia una carta o un muro en la celda
     */
    public void setCharacter(GameCharacter character) {
        if (character == null) {
            this.character = null;
        } else {
            if (wall != null) {
                throw new IllegalStateException("La celda contiene un muro actualmente");
            }
            if (card != null) {
                throw new IllegalStateException("La celda contiene una carta actualmente");
            }

            this.character = character;
        }
    }

    /**
     * @param card null o card. Lanza una excepcion si ya habia un personaje o un muro en la celda.
     */
    public void setCard(Card card) {
        if (card == null) {
            this.card = null;
        } else {
            if (wall != null) {
                throw new IllegalStateException("La celda contiene un muro actualmente");
            }
            if (character != null) {
                throw new IllegalStateException("La celda contiene un personaje actualmente");
            }
            this.card = card;
        }
    }

    /**
     * @return true si tiene suelo
     */
    public boolean hasFloor() {
        return floor != null;
    }

    /**
     * @return true si contiene algun cha
     */
    public boolean isOccupied() {
        return !hasWall() || !hasCharacter();
    }

    public boolean isFree(){
        return !isOccupied();
    }

    /**
     * @return true si hay una carta
     */
    public boolean hasCard(){
        return card != null;
    }

    /**
     * @return true si hay un muro
     */
    public boolean hasWall(){
        return wall != null;
    }

    /**
     * @return true si hay un character
     */
    public boolean hasCharacter(){
        return character != null;
    }

    public boolean hasWalkableFloor(){
        return hasFloor() && floor.isWalkable();
    }

    public boolean hasClimbableWall(){
        return hasWall() && wall.isClimbable();
    }

    public boolean characterCanMove() {
        return !hasCharacter() && (hasWalkableFloor() || hasClimbableWall());
    }

    //
    // Implementacion de Object
    //

    /**
     *
     * @return version en formato String de la celda
     */
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
