package org.thegoats.rolgar2.game.config;

public record DifficultyConfig(
        String name,
        CharacterConfig playerConfig,
        CharacterConfig enemyConfig
) {
}
