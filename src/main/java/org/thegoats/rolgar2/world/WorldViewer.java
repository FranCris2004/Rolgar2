package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;

import java.awt.*;
import java.util.Map;

public class WorldViewer {
    private final Map<Floor, Bitmap> floorBitmapMap;
    private final Map<Wall, Bitmap> wallBitmapMap;
    private final Map<Card, Bitmap> cardBitmapMap;
    private final Bitmap CharacterBitmap;
    private final int cellWidth;
    private final int cellHeight;
    private final Color gradientFrom;
    private final Color gradientTo;

    private WorldViewer(Map<Floor, Bitmap> floorBitmapMap,
                        Map<Wall, Bitmap> wallBitmapMap,
                        Map<Card, Bitmap> cardBitmapMap,
                        Bitmap CharacterBitmap,
                        Color gradientFrom,
                        Color gradientTo,
                        int cellWidth,
                        int cellHeight) {
        Assert.notNull(floorBitmapMap, "'floorBitmapMap' no puede ser nulo");
        Assert.notNull(wallBitmapMap, "'wallBitmapMap' no puede ser nulo");
        Assert.notNull(CharacterBitmap, "'cardBitmapMap' no puede ser nulo");
        Assert.notNull(CharacterBitmap, "'CharacterBitmap' no puede ser nulo");
        Assert.notNull(gradientFrom, "'gradientFrom' no puede ser nulo");
        Assert.notNull(gradientTo, "'gradientTo' no puede ser nulo");
        Assert.positive(cellWidth, "'cellWidth' debe ser positivo");
        Assert.positive(cellHeight, "'cellHeight' debe ser positivo");

        this.floorBitmapMap = floorBitmapMap;
        this.wallBitmapMap = wallBitmapMap;
        this.cardBitmapMap = cardBitmapMap;
        this.CharacterBitmap = CharacterBitmap;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.gradientFrom = gradientFrom;
        this.gradientTo = gradientTo;
    }

    public void showLayers(World world, int from, int to) {
        var max = Math.max(from, to);
        var min = Math.min(from, to);

        // imprime las capas desde abajo hacia arriba
        for (var i = min; i <= max; i++) {
            int relativeDepth = i - min; // va de 0 a max - min
            showLayer(world, i, relativeDepth);
        }
    }

    public void showLayer(World world, int layer, int relativeDepth) {
        Assert.notNull(world, "'world' no puede ser nulo");
        Assert.inRange(layer, 0, world.getLayerCount()-1, "layer debe estar entre 0 y world.getLayerCount()-1");

        Bitmap layerBitmap = new Bitmap(cellWidth * world.getColumnCount(), cellHeight * world.getRowCount());

        for (int row = 0; row < world.getRowCount(); row++) {
            for (int column = 0; column < world.getColumnCount(); column++) {
                var cell = world.getCell(row, column, layer);

                int offsetX = column * cellWidth;
                int offsetY = row * cellHeight;

                cell.getFloor().ifPresent(worldFloor ->
                        layerBitmap.pasteBitmap(floorBitmapMap.get(worldFloor), offsetX, offsetY));

                cell.getWall().ifPresent(worldWall ->
                        layerBitmap.pasteBitmap(wallBitmapMap.get(worldWall), offsetX, offsetY));

                cell.getCard().ifPresent(worldCard ->
                        layerBitmap.pasteBitmap(cardBitmapMap.get(worldCard), offsetX, offsetY));

                cell.getCharacter().ifPresent(worldCharacter ->
                        layerBitmap.pasteBitmap(CharacterBitmap, offsetX, offsetY));
            }
        }

        applyDepthEffect(layerBitmap.getImage(), relativeDepth);
    }

    // aplica un efecto de degradado dependiendo de la profundidad relativa de esta capa
    // con respecto a la capa con la que se empezó a dibujar
    private void applyDepthEffect(Image image, int relativeDepth) {
        // (obviamente, esto lo hizo chatgpt)

        if (!(image instanceof java.awt.image.BufferedImage bufferedImage)) {
            return;
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        // Crear un nuevo gráfico sobre la imagen existente
        Graphics2D g = bufferedImage.createGraphics();

        // Calcular la opacidad del degradado según la profundidad
        // Cuanto mayor sea relativeDepth, más fuerte será el efecto
        float alpha = Math.min(1.0f, Math.max(0.1f, Math.abs(relativeDepth) * 0.15f));

        // Crear degradado vertical
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
