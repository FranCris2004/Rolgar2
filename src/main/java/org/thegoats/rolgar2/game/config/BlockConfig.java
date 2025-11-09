package org.thegoats.rolgar2.game.config;

public record BlockConfig (
        String name,
        boolean isWalkable,
        boolean isClimbable) {
}
