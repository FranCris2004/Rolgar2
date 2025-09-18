package org.thegoats.rolgar2.entity;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

/**
 * Representa un objeto con posicion en el mundo
 * ejemplos: personajes, cartas, trampas, etc
 */
public abstract class Entity {
    private Position position;

    /**
     * @param position No null
     */
    protected Entity(Position position) {
        setPosition(position);
    }

    /** Compara la instancia invocadora con el objeto pasado por parametro
     * @param obj   Objeto con el que comparar
     * @return true si son el mismo objeto, false si son objetos distintos
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        Entity other = (Entity) obj;
        return this.position.equals(other.position);
    }

    /**
     * @return Un formato string de la instancia invocadora
     */
    @Override
    public String toString(){
        return "Entity[position=" + this.position.toString() + "]";
    }
    /**
     * @param position No null
     */
    public void setPosition(Position position) {
        Assert.notNull(position, "'position' no debe ser null");

        // TODO: Sincronizar la posicion de la entidad en la grilla del mundo

        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
