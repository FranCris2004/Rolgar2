package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.game.config.FloorConfig;
import org.thegoats.rolgar2.util.Assert;

/**
 * Representa el suelo de una celda
 * @param isWalkable true si se puede caminar sobre, false en caso contrario
 */
public record Floor(FloorConfig config, boolean isWalkable) {
    public Floor {
        Assert.notNull(config, "config no puede ser nulo");
    }

    public Floor(FloorConfig config) {
        this(config, config.isWalkable());
    }
}
