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

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getGradientFrom() {
        return gradientFrom;
    }

    public Color getGradientTo() {
        return gradientTo;
    }

    public void setCellWidth(int cellWidth) {
        Assert.positive(cellWidth, "'cellWidth' debe ser positivo");
        this.cellWidth = cellWidth;
    }

    public void setCellHeight(int cellHeight) {
        Assert.positive(cellHeight, "'cellHeight' debe ser positivo");
        this.cellHeight = cellHeight;
    }

    public void setBackgroundColor(Color backgroundColor) {
        Assert.notNull(backgroundColor, "'backgroundColor' no puede ser nulo.");
        this.backgroundColor = backgroundColor;
    }

    public void setGradientFrom(Color gradientFrom) {
        Assert.notNull(gradientFrom, "'gradientFrom' no puede ser nulo.");
        this.gradientFrom = gradientFrom;
    }

    public void setGradientTo(Color gradientTo) {
        Assert.notNull(gradientTo, "'gradientTo' no puede ser nulo.");
        this.gradientTo = gradientTo;
    }

    public void setFloorBitmapMap(Map<String, Bitmap> floorBitmapMap) {
        Assert.notNull(floorBitmapMap, "floorBitmapMap no puede ser nulo.");
        this.floorBitmapMap = floorBitmapMap;
    }

    public void setWallBitmapMap(Map<String, Bitmap> wallBitmapMap) {
        Assert.notNull(wallBitmapMap, "wallBitmapMap no puede ser nulo.");
        this.wallBitmapMap = wallBitmapMap;
    }

    public void setCardBitmapMap(Map<String, Bitmap> cardBitmapMap) {
        Assert.notNull(cardBitmapMap, "cardBitmapMap no puede ser nulo.");
        this.cardBitmapMap = cardBitmapMap;
    }

    public void setCharacterBitmap(Bitmap characterBitmap) {
        Assert.notNull(characterBitmap, "'characterBitmap' no puede ser nulo.");
        this.characterBitmap = characterBitmap;
    }

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
