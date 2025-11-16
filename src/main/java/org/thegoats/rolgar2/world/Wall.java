package org.thegoats.rolgar2.world;

/**
 * Representa un muro en una celda
 * @param isClimbable true si se puede trepar, false en caso contrario
 */
public record Wall(String name, boolean isClimbable) {
    @Override
    public boolean equals(Object o) {
        return o instanceof Wall && this.name.equals(((Wall) o).name);
    }
}
