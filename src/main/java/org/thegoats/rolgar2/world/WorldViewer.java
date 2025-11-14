package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.game.GameCharacter;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.util.io.BitmapViewer;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

public class WorldViewer {
    private final Map<Card, Bitmap> cardBitmapMap;
    private final int cellWidth;
    private final int cellHeight;
    private final Color backgroundColor;
    private final Color gradientFrom;
    private final Color gradientTo;

    public WorldViewer(Map<Floor, Bitmap> floorBitmapMap,
                        Map<Wall, Bitmap> wallBitmapMap,
                        Map<Card, Bitmap> cardBitmapMap,
                        Bitmap CharacterBitmap,
                        Color backgroundColor,
                        Color gradientFrom,
                        Color gradientTo,
                        int cellWidth,
                        int cellHeight) {
        Assert.notNull(CharacterBitmap, "'cardBitmapMap' no puede ser nulo");
        Assert.notNull(CharacterBitmap, "'CharacterBitmap' no puede ser nulo");
        Assert.notNull(backgroundColor, "'backgroundColor' no puede ser nulo");
        Assert.notNull(gradientFrom, "'gradientFrom' no puede ser nulo");
        Assert.notNull(gradientTo, "'gradientTo' no puede ser nulo");
        Assert.positive(cellWidth, "'cellWidth' debe ser positivo");
        Assert.positive(cellHeight, "'cellHeight' debe ser positivo");

        this.cardBitmapMap = cardBitmapMap;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.backgroundColor = backgroundColor;
        this.gradientFrom = gradientFrom;
        this.gradientTo = gradientTo;
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
            int relativeDepth = i - min;
            layersBitmap.pasteBitmap(createLayerBitmap(world, i, relativeDepth), 0, 0);
        }

        BitmapViewer.showBitmaps(Collections.singleton(layersBitmap));
    }

    private Bitmap createLayerBitmap(World world, int layer, int relativeDepth) {
        Assert.notNull(world, "'world' no puede ser nulo");
        Assert.inRange(layer, 0, world.getLayerCount()-1, "layer debe estar entre 0 y world.getLayerCount()-1");

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
                    try {
                        layerBitmap.pasteBitmap(floor.config().getBitmap(), offsetX, offsetY);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                cell.getWall().ifPresent(wall ->
                {
                    try {
                        layerBitmap.pasteBitmap(wall.config().getBitmap(), offsetX, offsetY);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                cell.getCard().ifPresent(worldCard ->
                        layerBitmap.pasteBitmap(cardBitmapMap.get(worldCard), offsetX, offsetY));

                cell.getCharacter().ifPresent(worldCharacter ->
                        layerBitmap.pasteBitmap(GameCharacter.getBitmap(), offsetX, offsetY));
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
