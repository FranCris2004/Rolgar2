package org.thegoats.rolgar2.game.config;

/**
 * @param representation caracter que representa a la configuracion de celda en MapConfig.mapData
 * @param spritePath path a la imagen de la celda
 * @param floorConfigName nombre de la configuracion del suelo de la celda
 * @param wallConfigName nombre de la configuracion del muro de la celda
 */
public record CellConfig(Character representation, String spritePath, String floorConfigName, String wallConfigName) {
}
