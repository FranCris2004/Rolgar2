package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Wall;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WallConfig(String name, String spritePath, Boolean isClimbable) {
    public WallConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isClimbable == null) {
            isClimbable = false;
        }
    }

    @JsonIgnore
    public Bitmap getBitmap() throws IOException {
        return Bitmap.loadFromFile(spritePath);
    }

    @JsonIgnore
    public Wall getWall() {
        return new Wall(name, isClimbable);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WallConfig && ((WallConfig) o).name().equals(name);
    }
}
