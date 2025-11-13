package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.game.config.WallConfig;
import org.thegoats.rolgar2.util.Assert;

/**
 * Representa un muro en una celda
 * @param isClimbable true si se puede trepar, false en caso contrario
 */
public record Wall(WallConfig config, boolean isClimbable) {
    public Wall {
        Assert.notNull(config, "config no puede ser nulo");
    }

    public Wall(WallConfig config) {
        this(config, config.isClimbable());
    }
}
