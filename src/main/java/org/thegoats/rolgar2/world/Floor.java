package org.thegoats.rolgar2.world;

/**
 * Representa el suelo de una celda
 * @param name nombre del bloque
 * @param isWalkable true si se puede caminar sobre, false en caso contrario
 */
public record Floor(String name, boolean isWalkable) {
    @Override
    public boolean equals(Object o) {
        return o instanceof Floor && this.name.equals(((Floor) o).name);
    }
}
