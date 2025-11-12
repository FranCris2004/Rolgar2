package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

public record DifficultyConfig(
        String name,
        CharacterConfig playerConfig,
        CharacterConfig enemyConfig
) {
    public DifficultyConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(playerConfig, "playerConfig no puede ser nulo");
        Assert.notNull(enemyConfig, "enemyConfig no puede ser nulo");
    }
}
