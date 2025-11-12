package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

import java.io.File;

public record WallConfig(String name, String spritePath, Boolean isClimbable) {
    public WallConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isClimbable == null) {
            isClimbable = false;
        }
    }

    public File getSpriteFile() {
        return new File(spritePath);
    }
}
