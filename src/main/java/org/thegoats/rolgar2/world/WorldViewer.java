package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.util.io.BitmapViewer;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

public class WorldViewer {
    private Map<String, Bitmap> floorBitmapMap;
    private Map<String, Bitmap> wallBitmapMap;
    private Map<String, Bitmap> cardBitmapMap;
    private Bitmap characterBitmap;
    private int cellWidth;
    private int cellHeight;
    private Color backgroundColor;
    private Color gradientFrom;
    private Color gradientTo;

    public WorldViewer(Map<String, Bitmap> floorBitmapMap,
                        Map<String, Bitmap> wallBitmapMap,
                        Map<String, Bitmap> cardBitmapMap,
                        Bitmap characterBitmap,
                        Color backgroundColor,
                        Color gradientFrom,
                        Color gradientTo,
                        int cellWidth,
                        int cellHeight) {
        setCellHeight(cellHeight);
        setCellWidth(cellWidth);
        setBackgroundColor(backgroundColor);
        setGradientFrom(gradientFrom);
        setGradientTo(gradientTo);
        setFloorBitmapMap(floorBitmapMap);
        setWallBitmapMap(wallBitmapMap);
        setCardBitmapMap(cardBitmapMap);
        setCharacterBitmap(characterBitmap);
    }

    /**
     * @return Devuelve el ancho en pixeles de una celda
     */
    public int getCellWidth() {
        return cellWidth;
    }

    /**
     * @return Devuelve el alto en pixeles de una celda
     */
    public int getCellHeight() {
        return cellHeight;
    }

    /**
     * @return Devuelve el color de fondo de una celda
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @return Devuelve el color inicial de un degradado
     */
    public Color getGradientFrom() {
        return gradientFrom;
    }

    /**
     * @return Devuelve el color final de un degradado
     */
    public Color getGradientTo() {
        return gradientTo;
    }

    /**
     * Establece el ancho en pixeles de una celda
     * @param cellWidth debe ser positivo
     */
    public void setCellWidth(int cellWidth) {
        Assert.positive(cellWidth, "'cellWidth' debe ser positivo");
        this.cellWidth = cellWidth;
    }

    /**
     * Establece el alto en pixeles de una celda
     * @param cellHeight debe ser positivo
     */
    public void setCellHeight(int cellHeight) {
        Assert.positive(cellHeight, "'cellHeight' debe ser positivo");
        this.cellHeight = cellHeight;
    }

    /**
     * Establece el color de fondo de una celda
     * @param backgroundColor no puede ser nulo
     */
    public void setBackgroundColor(Color backgroundColor) {
        Assert.notNull(backgroundColor, "'backgroundColor' no puede ser nulo.");
        this.backgroundColor = backgroundColor;
    }

    /**
     * Establece el color inicial de un degradado
     * @param gradientFrom no puede ser nulo
     */
    public void setGradientFrom(Color gradientFrom) {
        Assert.notNull(gradientFrom, "'gradientFrom' no puede ser nulo.");
        this.gradientFrom = gradientFrom;
    }

    /**
     * Establece el color final de un degradado
     * @param gradientTo no puede ser nulo
     */
    public void setGradientTo(Color gradientTo) {
        Assert.notNull(gradientTo, "'gradientTo' no puede ser nulo.");
        this.gradientTo = gradientTo;
    }

    /**
     * Establece el mapa de bitmaps de piso
     * @param floorBitmapMap no puede ser nulo
     */
    public void setFloorBitmapMap(Map<String, Bitmap> floorBitmapMap) {
        Assert.notNull(floorBitmapMap, "floorBitmapMap no puede ser nulo.");
        this.floorBitmapMap = floorBitmapMap;
    }

    /**
     * Establece el mapa de bitmaps de pared
     * @param wallBitmapMap no puede ser nulo
     */
    public void setWallBitmapMap(Map<String, Bitmap> wallBitmapMap) {
        Assert.notNull(wallBitmapMap, "wallBitmapMap no puede ser nulo.");
        this.wallBitmapMap = wallBitmapMap;
    }

    /**
     * Establece el mapa de bitmaps de cartas
     * @param cardBitmapMap no puede ser nulo
     */
    public void setCardBitmapMap(Map<String, Bitmap> cardBitmapMap) {
        Assert.notNull(cardBitmapMap, "cardBitmapMap no puede ser nulo.");
        this.cardBitmapMap = cardBitmapMap;
    }

    /**
     * Establece el bitmap utilizado para representar al personaje
     * @param characterBitmap no puede ser nulo
     */
    public void setCharacterBitmap(Bitmap characterBitmap) {
        Assert.notNull(characterBitmap, "'characterBitmap' no puede ser nulo.");
        this.characterBitmap = characterBitmap;
    }

    /**
     * Muestra las capas del mundo
     * @param world no puede ser nulo
     * @param layer debe ser positivo
     */
    public void showLayer(World world, int layer) {
        Assert.notNull(world, "'world' no puede ser nulo");
        Assert.inRange(layer, 0, world.getLayerCount() - 1,
                "layer debe estar entre 0 y world.getLayerCount()-1");
        Bitmap layerBitmap = new Bitmap(
                cellWidth * world.getColumnCount(),
                cellHeight * world.getRowCount()
        );

        layerBitmap.fill(backgroundColor);

        for (int row = 0; row < world.getRowCount(); row++) {
            for (int col = 0; col < world.getColumnCount(); col++) {

                var cell = world.getCell(row, col, layer);

                int x = col * cellWidth;
                int y = row * cellHeight;

                // floor
                cell.getFloor().ifPresent(floor -> {
                    var bmp = floorBitmapMap.get(floor.name());
                    if (bmp != null) {
                        var scaled = bmp.scaled(cellWidth, cellHeight);
                        layerBitmap.pasteBitmap(scaled, x, y);
                    }
                });

                // wall
                cell.getWall().ifPresent(wall -> {
                    var bmp = wallBitmapMap.get(wall.name());
                    if (bmp != null) {
                        var scaled = bmp.scaled(cellWidth, cellHeight);
                        layerBitmap.pasteBitmap(scaled, x, y);
                    }
                });

                // card
                cell.getCard().ifPresent(card -> {
                    var bmp = cardBitmapMap.get(card.getClass().getName());
                    if (bmp != null) {
                        var scaled = bmp.scaled(cellWidth, cellHeight);
                        layerBitmap.pasteBitmap(scaled, x, y);
                    }
                });

                // character
                cell.getCharacter().ifPresent(character -> {
                    var scaled = characterBitmap.scaled(cellWidth, cellHeight);
                    layerBitmap.pasteBitmap(scaled, x, y);
                });
            }
        }

        BitmapViewer.showBitmaps(Collections.singleton(layerBitmap));
    }
}
