package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Wall;

import java.io.IOException;

public record WallConfig(String name, String spritePath, Boolean isClimbable) {
    public WallConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isClimbable == null) {
            isClimbable = false;
        }
    }

    public Bitmap getBitmap() throws IOException {
        return Bitmap.loadFromFile(spritePath);
    }

    public Wall getWall() {
        return new Wall(name, isClimbable);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WallConfig && ((WallConfig) o).name().equals(name);
    }
}
