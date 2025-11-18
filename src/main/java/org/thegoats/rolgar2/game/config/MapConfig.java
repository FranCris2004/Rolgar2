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

    /**
     * Genera un mapa que relaciona el nombre de cada piso con su respectiva configuración
     * @return Devuelve un Map cuya clave es el nombre del piso y cuyo valor es su FloorConfig
     */
    @JsonIgnore
    public Map<String, FloorConfig> getFloorConfigsMap() {
        Map<String, FloorConfig> map = new HashMap<>();

        for (FloorConfig floorConfig : floorConfigs) {
            map.put(floorConfig.name(), floorConfig);
        }

        return map;
    }

    /**
     * Genera un mapa que relaciona el nombre de cada pared con su respectiva configuración
     * @return Devuelve un Map cuya clave es el nombre de la pared y cuyo valor es su WallConfig
     */
    @JsonIgnore
    public Map<String, WallConfig> getWallConfigsMap() {
        Map<String, WallConfig> map = new HashMap<>();

        for (WallConfig wallConfig : wallConfigs) {
            map.put(wallConfig.name(), wallConfig);
        }

        return map;
    }

    /**
     * Carga los bitmaps asociados a las configuraciones de piso
     * @return Devuelve un Map cuya clave es el nombre del piso y cuyo valor es el Bitmap correspondiente
     * @throws IOException si ocurre un error al cargar alguno de los archivos de imagen de piso
     */
    @JsonIgnore
    public Map<String, Bitmap> getFloorBitmapMap() throws IOException {
        Map<String, Bitmap> map = new HashMap<>();

        for (FloorConfig floorConfig : floorConfigs) {
            map.put(floorConfig.name(), floorConfig.getBitmap());
        }

        return map;
    }

    /**
     * Carga los bitmaps asociados a las configuraciones de pared
     * @return Devuelve un Map cuya clave es el nombre de la pared y cuyo valor es el Bitmap correspondiente
     * @throws IOException si ocurre un error al cargar alguno de los archivos de imagen de pared
     */
    @JsonIgnore
    public Map<String,Bitmap> getWallBitmapMap() throws IOException {
        Map<String, Bitmap> map = new HashMap<>();

        for (WallConfig wallConfig : wallConfigs) {
            map.put(wallConfig.name(), wallConfig.getBitmap());
        }

        return map;
    }

    /**
     * Genera una instancia completa de World construido a partir de los datos de configuracion del mapa
     * @return Devuelve dicho objeto World
     */
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
