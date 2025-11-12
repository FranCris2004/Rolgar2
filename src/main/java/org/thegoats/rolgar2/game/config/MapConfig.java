package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

import java.util.Arrays;

public record MapConfig(
        String name,
        FloorConfig[] floorConfigs,
        WallConfig[] wallConfigs,
        CellConfig[] cellConfigs,
        Character[][][] mapData
) {
    public MapConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(floorConfigs, "floorConfigs no puede ser nulo");
        Assert.notNull(wallConfigs, "wallConfigs no puede ser nulo");
        Assert.notNull(cellConfigs, "cellConfigs no puede ser nulo");
        Assert.notNull(mapData, "mapData no puede ser nulo");

        for (CellConfig cellConfig : cellConfigs) {
            Assert.isTrue(Arrays.stream(wallConfigs)
                            .anyMatch(wallConfig -> wallConfig.name() == cellConfig.wallConfigName()),
                    "'%s' no coincide con ninguna configuracion de muros en wallConfigs".formatted(cellConfig.wallConfigName()));

            Assert.isTrue(Arrays.stream(floorConfigs)
                            .anyMatch(floorConfig -> floorConfig.name() == cellConfig.floorConfigName()),
                    "'%s' no coincide con ninguna configuracion de suelos en floorConfigs".formatted(cellConfig.floorConfigName()));
        }

        for (int i = 0; i < mapData.length; i++) {
            Assert.notNull(mapData[i], "mapData[%d] no puede ser nulo".formatted(i));
            for (int j = 0; j < mapData.length; j++) {
                Assert.notNull(mapData[i][j], "mapData[%d][%d] no puede ser nulo".formatted(i, j));
                for (int k = 0; k < mapData.length; k++) {
                    Assert.notNull(mapData[i][j][k], "mapData[%d][%d][%d] no puede ser nulo".formatted(i, j, k));
                    var cellRepresentation = mapData[i][j][k];
                    Assert.isTrue(Arrays.stream(cellConfigs)
                            .anyMatch(cellConfig -> cellConfig.representation() == cellRepresentation),
                            "mapData[%d][%d][%d] no coincide con ninguna configuracion de celda en cellConfig".formatted(i, j, k));
                }
            }
        }
    }
}
