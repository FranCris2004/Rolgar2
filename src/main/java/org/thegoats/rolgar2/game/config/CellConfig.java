package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

public record CellConfig(
        String floor,
        String wall
) {
    public CellConfig {
        Assert.isTrue(!(floor == null && wall != null), "Si no hay suelo entonces no puede haber muro.");
    }
}
