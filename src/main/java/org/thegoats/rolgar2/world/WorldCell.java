package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class WorldCell {
    //
    // Atributos privados
    //

    private Object content;
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
        setNull();
        this.position = position;
    }

    /**
     * Crea e inicializa una celda de posicion position y con un personaje como contenido
     * @param position no null, posicion de la celda en el tablero
     * @param character no null, personaje a ubicar en la celda
     */
    public WorldCell(Position position, CharacterData character) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        this.position = position;
        setCharacter(character);
    }

    /**
     * Crea e inicializa una nueva celda de posicion position y con un bloque como contenido
     * @param position no null, posicion de la celda e el tablero
     * @param block no null, bloque a ubicar en la celda
     */
    public WorldCell(Position position, Block block) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        this.position = position;
        setBlock(block);
    }

    public WorldCell(Position position, Card card){
        Assert.notNull(position, "position no puede ser null");
        setCard(card);
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
     * Devuelve el contenido de la celda ya sea bloque, null, personaje o carta
     * @return contenido de la celda
     */
    public Object get() {
        return content;
    }

    /**
     * Devuelve el contenido de la celda casteado como CharacterData
     * @return personaje contenido en la celda
     * @throws IllegalStateException si no habia ningun personaje en la celda
     */
    public CharacterData getCharacter() {
        if (!hasCharacter()) {
            throw new IllegalStateException("No hay un Character que obtener en la celda.");
        }

        return ((CharacterData)content);
    }

    /**
     * Si hay un bloque en la celda, lo devuelve, sino lanza una excepcion
     * @return Bloque contenido en la celda
     * @throws  IllegalStateException si no hay bloques en la celda
     */
    public Block getBlock() {
        if (!hasBlock()) {
            throw new IllegalStateException("No hay un Block que obtener en la celda.");
        }
        return ((Block)content);
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

    /**
     * @return iterador de las celdas vecinas
     */
    public Iterator<WorldCell> getNeighborsIterator() {
        return neighbors.iterator();
    }

    /**
     * Obtiene el bloque debajo de la celda invocadora
     * @return celda o null
     */
    public WorldCell getFloor() {
        return neighbors.stream()
                .filter(WorldCell::hasBlock)
                .filter((neighbor) ->
                        this.getPosition().getRow() == neighbor.getPosition().getRow() &&
                        this.getPosition().getColumn() == neighbor.getPosition().getColumn() &&
                        this.getPosition().getLayer() == neighbor.getPosition().getLayer() + 1
                ).findFirst().orElse(null);
    }

    //
    // Setters
    //

    /**
     * Setea null a la celda
     */
    public void setNull() {
        this.content = null;
    }

    /**
     *
     * @param characterData no null, personaje a ubicar en la celda
     */
    public void setCharacter(CharacterData characterData) {
        Assert.notNull(characterData, "characterData no debe ser null");
        WorldCell floor = this.getFloor();
        Assert.isTrue(hasWalkableFloor(), "la celda debajo deberia contener un bloque caminable");
        this.content = characterData;
    }

    /**
     * @param block no null, bloque a ubicar en la celda
     */
    public void setBlock(Block block) {
        Assert.notNull(block, "block no debe ser null");
        this.content = block;
    }

    /**
     * @param card no null, carta a ubicar en la celda
     */
    public void setCard(Card card){
        Assert.notNull(card, "card no puede ser null");
        Assert.isTrue(hasWalkableFloor(), "la celda debajo deberia contener un bloque caminable");
        this.content = card;
    }

    //
    // Validaciones de estado
    //

    /**
     * @return true si this.content == null
     */
    public boolean hasNull() {
        return content == null;
    }

    /**
     * @return true si contiene CharacterData
     */
    public boolean hasCharacter()
    {
        return !hasNull() && content instanceof CharacterData;
    }

    /**
     * @return true si contiene Block
     */
    public boolean hasBlock()
    {
        return !hasNull() && content instanceof Block;
    }

    /**
     * @return true si la celda debajo contiene un bloque caminable
     */
    public boolean hasWalkableFloor(){
        if(getFloor() != null && getFloor().hasBlock()){
            return getFloor().getBlock().isWalkable();
        }
        return false;
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
                "WorldCell[position=%s, content=%s]",
                position, content
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
