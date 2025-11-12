package org.thegoats.rolgar2.game.config;

public record MapConfig(
        String name,
        FloorConfig[] floorConfigs,
        WallConfig[] wallConfigs,
        CellConfig[] cellConfigs,
        Character[][][] mapData
) {

}
