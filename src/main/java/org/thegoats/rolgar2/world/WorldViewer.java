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

    public void showLayers(World world, int from, int to) {
        var max = Math.max(from, to);
        var min = Math.min(from, to);

        Bitmap layersBitmap = new Bitmap(
                cellWidth * world.getColumnCount(),
                cellHeight * world.getRowCount()
        );

        // CORRECCIÓN: dibujar el fondo completo
        layersBitmap.fill(backgroundColor);

        // pintar capas
        for (var i = min; i <= max; i++) {
            int relativeDepth = i - min - 1;
            var layerBitmap = createLayerBitmap(world, i, relativeDepth);
            layersBitmap.pasteBitmap(layerBitmap, 0, 0);
        }

        BitmapViewer.showBitmaps(Collections.singleton(layersBitmap));
    }

    private Bitmap createLayerBitmap(World world, int layer, int relativeDepth) {
        Assert.notNull(world, "'world' no puede ser nulo");
        Assert.inRange(layer, 0, world.getLayerCount()-1, "layer");

        Bitmap layerBitmap = new Bitmap(cellWidth * world.getColumnCount(), cellHeight * world.getRowCount());

        layerBitmap.fill(new Color(255, 0, 255, 0));

        // dibuja el contenido
        for (int row = 0; row < world.getRowCount(); row++) {
            for (int column = 0; column < world.getColumnCount(); column++) {
                var cell = world.getCell(row, column, layer);

                int offsetX = column * cellWidth;
                int offsetY = row * cellHeight;

                cell.getFloor().ifPresent(floor ->
                {
                    var bitmap = floorBitmapMap.get(floor.name()).copy();
                    if (bitmap != null) {
                        bitmap.scale(cellWidth, cellHeight);
                        layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                    }
                });

                cell.getWall().ifPresent(wall ->
                {
                    var bitmap = wallBitmapMap.get(wall.name());
                    if (bitmap != null) {
                        bitmap.scale(cellWidth, cellHeight);
                        layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                    }
                });

                cell.getCard().ifPresent(card -> {
                    var bitmap = cardBitmapMap.get(card.getClass().getName()).copy();
                    bitmap.scale(cellWidth, cellHeight);
                    layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                });

                cell.getCharacter().ifPresent(worldCharacter -> {
                    var bitmap = characterBitmap.copy();
                    bitmap.scale(cellWidth, cellHeight);
                    layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                });
            }
        }

        // aplica el efecto de profundidad
        applyDepthEffect(layerBitmap.getImage(), relativeDepth);

        return layerBitmap;
    }

    // aplica un efecto de degradado dependiendo de la profundidad relativa de esta capa
    // con respecto a la capa con la que se empezó a dibujar
    private void applyDepthEffect(Image image, int relativeDepth) {
        if (!(image instanceof java.awt.image.BufferedImage bufferedImage)) return;

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Graphics2D g = bufferedImage.createGraphics();

        float alpha = 0.05f + (relativeDepth * 0.05f);
        if (alpha > 0.40f) alpha = 0.40f;  // nunca opacar del todo

        GradientPaint gradient = new GradientPaint(
                0, 0, gradientFrom,
                0, height, gradientTo
        );

        g.setPaint(gradient);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.fillRect(0, 0, width, height);

        g.dispose();
    }
}
