package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Floor;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FloorConfig(String name, String spritePath, Boolean isWalkable) {
    public FloorConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isWalkable == null) {
            isWalkable = false;
        }
    }

    @JsonIgnore
    public Bitmap getBitmap() throws IOException {
        return Bitmap.loadFromFile(spritePath);
    }

    @JsonIgnore
    public Floor getFloor() {
        return new Floor(name, isWalkable);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FloorConfig && ((FloorConfig) o).name.equals(name);
    }
}
