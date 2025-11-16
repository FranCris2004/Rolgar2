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
     * @param from debe ser positivo
     * @param to debe ser positivo
     */
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

    /**
     * Crea un bitmap para una capa del mundo
     * @param world no puede ser nulo
     * @param layer debe estar entre 0 y el numero de capas del mundo - 1
     * @param relativeDepth distancia relativa respecto de la primera capa
     * @return Devuelve un Bitmap representando graficamente la capa
     */
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
                    var bitmap = floorBitmapMap.get(floor.name());
                    if (bitmap != null) {
                        layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                    }
                });

                cell.getWall().ifPresent(wall ->
                {
                    var bitmap = wallBitmapMap.get(wall.name());
                    if (bitmap != null) {
                        layerBitmap.pasteBitmap(bitmap, offsetX, offsetY);
                    }
                });

                cell.getCard().ifPresent(card ->
                        layerBitmap.pasteBitmap(cardBitmapMap.get(card.getClass().getName()), offsetX, offsetY));

                cell.getCharacter().ifPresent(worldCharacter ->
                        layerBitmap.pasteBitmap(characterBitmap, offsetX, offsetY));
            }
        }

        // aplica el efecto de profundidad
        applyDepthEffect(layerBitmap.getImage(), relativeDepth);

        return layerBitmap;
    }

    /**
     * Aplica un efecto de degradado dependiendo de la profundidad relativa de esta capa
     * con respecto a la capa con la que se empezó a dibujar
     * @param image es la imagen sobre la cual se aplica el efecto, debe ser una BufferedImage
     * @param relativeDepth es el nivel de profundidad relativo usado para calcular la opacidad
     */
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
