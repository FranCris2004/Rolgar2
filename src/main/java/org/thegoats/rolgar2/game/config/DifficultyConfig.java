package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.game.config.card.CardConfigBundle;
import org.thegoats.rolgar2.util.Assert;

public record DifficultyConfig(
        String name,
        CharacterConfig playerConfig,
        CharacterConfig enemyConfig,
        CardConfigBundle cardConfig
) {
    public DifficultyConfig {
        Assert.notNull(name, "name");
        Assert.notNull(playerConfig, "playerConfig");
        Assert.notNull(enemyConfig, "enemyConfig");
        Assert.notNull(cardConfig, "cardConfig");
    }

    @Override
    public String toString() {
        return name;
    }
}
