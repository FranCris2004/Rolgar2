package org.thegoats.rolgar2.entity;

import org.thegoats.rolgar2.world.Position;

/**
 * Representa un objeto con posicion en el mundo
 * ejemplos: personajes, cartas, trampas, etc
 */
public class Entity {
    public Position position;

    /**
     * @param position No null
     */
    public Entity(Position position) {
        setPosition(position);
    }


    @Override
    public String toString(){
        return "Entity[position=" + this.position.toString() + "]";
    }
    /**
     * @param position No null
     */
    public void setPosition(Position position) {
        if (position == null) {
            throw new NullPointerException("'position' no debe ser null");
        }

        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
