package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public WorldCell(Position position) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        setNull();
        this.position = position;
    }
    
    public WorldCell(Position position, CharacterData character) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        set(character);
        this.position = position;
    }

    public WorldCell(Position position, Block block) {
        Assert.notNull(position, "'position' debe ser no nulo.");
        set(block);
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

    public Object get() {
        return content;
    }
    
    public Optional<CharacterData> getCharacter() {
        return Optional.ofNullable((CharacterData)content);
    }
    
    public Optional<Block> getBlock() {
        return Optional.ofNullable((Block)content);
    }

    public Position getPosition() {
        return position;
    }

    public List<WorldCell> getNeighbors() {
        return List.copyOf(neighbors);
    }

    public Iterator<WorldCell> getNeighborsIterator() {
        return neighbors.iterator();
    }

    //
    // Setters
    //
    
    public void setNull() {
        this.content = null;
    }
    
    public void set(CharacterData characterData) {
        this.content = characterData;
    }
    
    public void set(Block block) {
        this.content = block;
    }

    //
    // Validaciones de estado
    //

    public boolean hasNull() {
        return content == null;
    }

    public boolean hasCharacter()
    {
        return !hasNull() && content instanceof CharacterData;
    }

    public boolean hasBlock()
    {
        return !hasNull() && content instanceof Block;
    }

    //
    // Implementacion de Object
    //

    @Override
    public String toString() {
        return String.format(
                "WorldCell[position=%s, content=%s, neighbors=%s]",
                position, content, neighbors
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
