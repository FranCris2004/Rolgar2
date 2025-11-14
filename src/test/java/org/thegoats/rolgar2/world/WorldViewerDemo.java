package org.thegoats.rolgar2.world;// Code generated using ChatGPT.

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.util.io.Bitmap;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldViewerDemo {
    public static void main(String[] args) {

        //
        // World
        //

        World world = new World(10, 10, 10);

        Floor grassFloor = new Floor("Grass", true);
        Floor rockFloor = new Floor("Rock", true);
        Floor iceFloor = new Floor("Ice", false);
        Wall brickWall = new Wall("Bricks", false);

        for (int row = 0; row < world.getColumnCount()/2; row++) {
            for (int column = 0; column < world.getColumnCount()/2; column++) {
                if (column % 2 == 0) {
                    world.getCell(row, column, 1).setFloor(grassFloor);
                }
            }
            for (int column = world.getRowCount()/2; column < world.getRowCount(); column++) {
                if (row % 2 == 0) {
                    world.getCell(row, column, 2).setFloor(rockFloor);
                }
            }
        }

        for (int layer = 0; layer < world.getLayerCount(); layer++) {
            for (int row = 0; row < world.getRowCount(); row++) {
                for (int column = 0; column < world.getColumnCount(); column++) {
                    if (layer == column && layer == row) {
                        world.getCell(row, column, layer).setFloor(iceFloor);
                        world.getCell(row, world.getColumnCount() - column - 1, layer).setFloor(grassFloor);
                    }
                }
            }
        }

        //
        // WorldViewer
        //

        // === Bitmap “mock” para la demo ===
        // En tu juego real cargarías bitmaps desde disco.
        Bitmap grassFloorBmp  = new Bitmap(32, 32);
        Bitmap rockFloorBmp = new Bitmap(32, 32);
        Bitmap iceFloorBmp = new Bitmap(32, 32);
        Bitmap charBmp   = new Bitmap(32, 32);

        // Los pintamos de colores para notar diferencia visual
        grassFloorBmp.fill(Color.GREEN);
        rockFloorBmp.fill(Color.LIGHT_GRAY);
        iceFloorBmp.fill(Color.CYAN);

        // === Mapeos requeridos por WorldViewer ===
        Map<String, Bitmap> floorMap = new HashMap<>();
        floorMap.put(grassFloor.name(), grassFloorBmp);
        floorMap.put(rockFloor.name(), rockFloorBmp);
        floorMap.put(iceFloor.name(), iceFloorBmp);

        Map<String, Bitmap> wallMap = new HashMap<>();

        Map<String, Bitmap> cardMap = new HashMap<>();

        // === Crear viewer con parámetros reales ===
        WorldViewer viewer = new WorldViewer(
                floorMap,
                wallMap,
                cardMap,
                charBmp,
                Color.BLACK,
                new Color(0, 0, 0, 0),        // gradientFrom
                new Color(0, 0, 0, 180),      // gradientTo
                32,
                32
        );

        for (int layer = -5; layer < world.getLayerCount()+6; layer++) {
            System.out.println("Mostrando las capas " + (layer-5) + "-" + layer);
            viewer.showLayers(world, Math.max(0, layer-5), Math.min(layer+5, world.getLayerCount()-1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
