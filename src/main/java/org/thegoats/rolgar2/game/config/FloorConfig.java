package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

import java.io.File;

public record FloorConfig(String name, String spritePath, Boolean isWalkable) {
    public FloorConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isWalkable == null) {
            isWalkable = false;
        }
    }

    public File getSpriteFile() {
        return new File(spritePath);
    }
}
