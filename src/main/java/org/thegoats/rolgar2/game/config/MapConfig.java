package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Floor;
import org.thegoats.rolgar2.world.Wall;
import org.thegoats.rolgar2.world.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MapConfig(
        String name,
        FloorConfig[] floorConfigs,
        WallConfig[] wallConfigs,
        CellConfig[][][] mapData
) {
    public MapConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(floorConfigs, "floorConfigs no puede ser nulo");
        Assert.notNull(wallConfigs, "wallConfigs no puede ser nulo");
        Assert.notNull(mapData, "mapData no puede ser nulo");

        Assert.positive(mapData.length, "mapData.length debe ser positivo");
        Assert.positive(mapData[0].length, "mapData[0].length debe ser positivo");
        Assert.positive(mapData[0][0].length, "mapData[0][0].length debe ser positivo");
    }

    @JsonIgnore
    public Map<String, FloorConfig> getFloorConfigsMap() {
        Map<String, FloorConfig> map = new HashMap<>();

        for (FloorConfig floorConfig : floorConfigs) {
            map.put(floorConfig.name(), floorConfig);
        }

        return map;
    }

    @JsonIgnore
    public Map<String, WallConfig> getWallConfigsMap() {
        Map<String, WallConfig> map = new HashMap<>();

        for (WallConfig wallConfig : wallConfigs) {
            map.put(wallConfig.name(), wallConfig);
        }

        return map;
    }

    @JsonIgnore
    public Map<String, Bitmap> getFloorBitmapMap() throws IOException {
        Map<String, Bitmap> map = new HashMap<>();

        for (FloorConfig floorConfig : floorConfigs) {
            map.put(floorConfig.name(), floorConfig.getBitmap());
        }

        return map;
    }

    @JsonIgnore
    public Map<String,Bitmap> getWallBitmapMap() throws IOException {
        Map<String, Bitmap> map = new HashMap<>();

        for (WallConfig wallConfig : wallConfigs) {
            map.put(wallConfig.name(), wallConfig.getBitmap());
        }

        return map;
    }

    @JsonIgnore
    public World generateWorld() {
        World world = new World(mapData[0][0].length, mapData[0].length, mapData.length);
        var floorConfigsMap = getFloorConfigsMap();
        var wallConfigsMap = getWallConfigsMap();

        for (int layer = 0; layer < mapData.length; layer++) {
            for (int row = 0; row < mapData[0].length; row++) {
                for (int column = 0; column < mapData[0][0].length; column++) {
                    CellConfig cellConfig = mapData[layer][row][column];

                    if (cellConfig != null) {
                        var cell = world.getCell(row, column, layer);

                        FloorConfig floorConfig = floorConfigsMap.getOrDefault(cellConfig.floor(), null);
                        if (floorConfig != null) {
                            cell.setFloor(floorConfigsMap.getOrDefault(cellConfig.floor(), null).getFloor());
                        }

                        WallConfig wallConfig = wallConfigsMap.getOrDefault(cellConfig.wall(), null);
                        if (wallConfig != null) {
                            cell.setWall(wallConfigsMap.getOrDefault(cellConfig.wall(), null).getWall());
                        }
                    }
                }
            }
        }

        return world;
    }

    @Override
    public String toString() {
        return name;
    }
}
