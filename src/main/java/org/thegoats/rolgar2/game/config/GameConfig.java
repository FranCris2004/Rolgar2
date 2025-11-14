package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

public record GameConfig(
        DifficultyConfig difficultyConfig,
        MapConfig mapConfig
) {
    public GameConfig {
        Assert.notNull(difficultyConfig, "difficultyConfig no puede ser nulo");
        Assert.notNull(mapConfig, "mapConfig no puede ser nulo");
    }
}
