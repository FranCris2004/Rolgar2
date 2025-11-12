package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

/**
 * @param representation caracter que representa a la configuracion de celda en MapConfig.mapData
 * @param spritePath path a la imagen de la celda
 * @param floorConfigName nombre de la configuracion del suelo de la celda
 * @param wallConfigName nombre de la configuracion del muro de la celda
 */
public record CellConfig(Character representation, String spritePath, String floorConfigName, String wallConfigName) {
    public CellConfig {
        Assert.notNull(representation, "representation no puede ser nulo.");
        Assert.notNull(spritePath, "spritePath no puede ser nulo.");
        Assert.notNull(floorConfigName, "floorConfigName no puede ser nulo.");
        Assert.notNull(wallConfigName, "wallConfigName no puede ser nulo.");
    }
}
