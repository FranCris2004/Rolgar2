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


/**
 * Representa la configuración de un mapa del juego cargada desde JSON
 * @param name nombre del mapa
 * @param floorConfigs configuraciones de los distintos tipos de piso disponibles
 * @param wallConfigs configuraciones de los distintos tipos de pared disponibles
 * @param mapData arreglo tridimensional con la información de cada celda
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MapConfig(
        String name,
        FloorConfig[] floorConfigs,
        WallConfig[] wallConfigs,
        CellConfig[][][] mapData
) {
    /**
     * Constructor del record. Ademas de asignar los campos
     * valida que no sean null ciertos campos y otros que sean positivos
     * si alguna validacion no se cumple lanza excepcion
     */
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
     *Crea un mapa auxiliar que permite buscar rápidamente una FloorConfig por su nombre.
     * @return un mapa donde la clave es el nombre del piso y el valor es la FloorConfig correspondiente
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
     * Crea un mapa auxiliar que permite buscar rápidamente una WallConfig por su nombre.
     * @return un mapa donde la clave es el nombre de la pared y el valor es la WallConfig correspondiente
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
     * Devuelve un mapa que asocia el nombre de cada tipo de piso con su Bitmap.
     * Para cada FloorConfig se carga o se obtiene el Bitmap correspondiente a la textura de ese piso.
     * @return un mapa donde la clave es el nombre del piso y el valor es su Bitmap
     * @throws IOException si ocurre un problema al cargar alguna imagen de piso
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
     * Devuelve un mapa que asocia el nombre de cada tipo de pared con su Bitmap.
     * Para cada WallConfig se carga o se obtiene el Bitmap correspondiente
     * a la textura de esa pared.
     * @return un mapa donde la clave es el nombre de la pared y el valor es su Bitmap
     * @throws IOException si ocurre un problema al cargar alguna imagen de pared
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
     * Genera y devuelve un objeto World a partir de esta configuración.
     * @return un World con todas sus celdas configuradas según mapData, floorConfigs y wallConfigs
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

    /**
     * Devuelve el nombre del mapa.
     * @return el nombre del mapa
     */
    @Override
    public String toString() {
        return name;
    }
}
