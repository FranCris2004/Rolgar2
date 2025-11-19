package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

/**
 * Representa la configuración de una celda del mapa
 * @param floor tipo de piso identificado por nombre
 * @param wall tipo de pared identificado por nombre
 */
public record CellConfig(
        String floor,
        String wall
) {
    /**
     * Constructor del record.
     * Valida que no exista una celda sin piso pero con pared.
     */
    public CellConfig {
        Assert.isTrue(!(floor == null && wall != null), "Si no hay suelo entonces no puede haber muro.");
    }
}
