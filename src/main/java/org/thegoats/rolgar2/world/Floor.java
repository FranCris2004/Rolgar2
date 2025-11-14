package org.thegoats.rolgar2.world;

/**
 * Representa el suelo de una celda
 * @param isWalkable true si se puede caminar sobre, false en caso contrario
 */
public record Floor(boolean isWalkable) {
}
