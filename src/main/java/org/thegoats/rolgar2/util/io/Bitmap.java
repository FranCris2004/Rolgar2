package org.thegoats.rolgar2.util.io;

import org.thegoats.rolgar2.util.Assert;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bitmap {
	private int width;
    private int height;
    private BufferedImage image;
    private double distanceToCamera = 200;
    
    /**
     * Genera un bitmap de ancho y alto dados
     * @param width ancho en pixeles mayor a 0
     * @param height alto en pixeles mayor a 0
     */
    public Bitmap(int width, int height) {
    	Assert.positive(width, "ancho");
        Assert.positive(height, "alto");
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Dibuja un pixel de un color determinado
     * @param x: pixel entre 1 y ancho
     * @param y: pixel entre 1 y alto
     * @param color: no nulo
     */
    public void drawPixel(int x, int y, Color color) {
    	Assert.inRange(0, getWidth(), x, "ancho");
    	Assert.inRange(0, getHeight(), y, "alto");
    	Assert.notNull(color, "color");
        if (x >= 0 && x < width && y >= 0 && y < height) {
            image.setRGB(x, y, color.getRGB());
        }
    }

    /**
     * Dibuja una linea del color dado
     * @param x1: pixel entre 1 y ancho (origen)
     * @param y1: pixel entre 1 y alto (origen)
     * @param x2: pixel entre 1 y ancho (destino)
     * @param y2: pixel entre 1 y alto (destino)
     * @param color: no nulo
     */
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
    	Assert.inRange(0, getWidth(), x1, "x1");
    	Assert.inRange(0, getWidth(), x2, "x2");
    	Assert.inRange(0, getHeight(), y1, "y1");
    	Assert.inRange(0, getHeight(), y2, "y2");
    	Assert.notNull(color, "color");
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            drawPixel(x1, y1, color);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    /**
     * Dibuja un rectangulo del color dado
     * @param x: pixel entre 1 y ancho (origen)
     * @param y: pixel entre 1 y alto (origen)
     * @param width: pixel entre 1 y ancho (destino)
     * @param height: pixel entre 1 y alto (destino)
     * @param color: no nulo
     */    
    public void drawRectangle(int x, int y, int width, int height, Color color) {
    	Assert.inRange(0, getWidth(), x, "x1");
    	Assert.inRange(0, getHeight(), y, "y1");
    	Assert.inRange(0, getWidth(), width, "ancho");
    	Assert.inRange(0, getHeight(), height, "alto");
    	Assert.notNull(color, "color");
        drawLine(x, y, x + width, y, color);
        drawLine(x, y, x, y + height, color);
        drawLine(x + width, y, x + width, y + height, color);
        drawLine(x, y + height, x + width, y + height, color);
    }

    /**
     * Dibuja un circulo del color dado
     * @param centerX: pixel entre 1 y ancho (origen)
     * @param centerY: pixel entre 1 y alto (origen)
     * @param radius: mayor a cero
     * @param color
     */
    public void drawCircle(int centerX, int centerY, int radius, Color color) {
    	Assert.inRange(0, getWidth(), centerX, "x1");
    	Assert.inRange(0, getHeight(), centerY, "y1");
    	Assert.inRange(0, Math.min(getWidth(), getHeight()), radius, "ancho");
    	Assert.notNull(color, "color");
        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y) {
            drawPixel(centerX + x, centerY + y, color);
            drawPixel(centerX + y, centerY + x, color);
            drawPixel(centerX - y, centerY + x, color);
            drawPixel(centerX - x, centerY + y, color);
            drawPixel(centerX - x, centerY - y, color);
            drawPixel(centerX - y, centerY - x, color);
            drawPixel(centerX + y, centerY - x, color);
            drawPixel(centerX + x, centerY - y, color);

            y++;
            if (err <= 0) {
                err += 2 * y + 1;
            }
            if (err > 0) {
                x--;
                err -= 2 * x + 1;
            }
        }
    }

    /**
     * Dibuja un texto en la posicion dada
     * @param text: texto a dibujar
     * @param x: pixel entre 1 y ancho (origen)
     * @param y: pixel entre 1 y alto (origen)
     * @param font: fuente a utilizar, no nula
     * @param color: colo no nulo
     */
    public void drawText(String text, int x, int y, Font font, Color color, Color background) {
    	Assert.inRange(0, getWidth(), x, "x");
    	Assert.inRange(0, getHeight(), y, "y");
    	Assert.notNull(font, "fuente");
    	Assert.notNull(color, "color");
        Graphics2D g = image.createGraphics();

        g.setColor(background);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int textAscent = fm.getAscent();
        int paddingSuperior = 5;
        int paddingInferior = 10;
        g.fillRect(x-paddingInferior, y - textAscent - paddingInferior, textWidth + (paddingSuperior*4), textHeight + (paddingSuperior*2));
        
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
        g.dispose();
    }

    public void pasteBitmap(Bitmap other, int x, int y) {
        Graphics2D g = image.createGraphics();
        g.drawImage(other.image, x, y, null);
        g.dispose();
    }

    public String saveToFile(String path) throws IOException {
    	File output = new File(path);
        ImageIO.write(image, "bmp", output);
        return output.getAbsolutePath();
    }

    public static Bitmap loadFromFile(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        Bitmap bmp = new Bitmap(img.getWidth(), img.getHeight());
        bmp.image = img;
        return bmp;
    }
    
    private int projectX(double x, double z) {
        return (int) (this.width / 2 + x * distanceToCamera / (z + distanceToCamera));
    }

    private int projectY(double y, double z) {
        return (int) (this.height / 2 - y * distanceToCamera / (z + distanceToCamera));
    }

    public void drawLine3D(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
        int x1p = projectX(x1, z1);
        int y1p = projectY(y1, z1);
        int x2p = projectX(x2, z2);
        int y2p = projectY(y2, z2);
        this.drawLine(x1p, y1p, x2p, y2p, color);
    }

    public void drawCube(double size, double cx, double cy, double cz, Color color) {
        double[][] vertices = new double[8][3];
        int i = 0;
        for (int dx = -1; dx <= 1; dx += 2) {
            for (int dy = -1; dy <= 1; dy += 2) {
                for (int dz = -1; dz <= 1; dz += 2) {
                    vertices[i][0] = cx + dx * size / 2;
                    vertices[i][1] = cy + dy * size / 2;
                    vertices[i][2] = cz + dz * size / 2;
                    i++;
                }
            }
        }

        int[][] edges = {
            {0, 1}, {1, 3}, {3, 2}, {2, 0},
            {4, 5}, {5, 7}, {7, 6}, {6, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        for (int[] edge : edges) {
            double[] p1 = vertices[edge[0]];
            double[] p2 = vertices[edge[1]];
            drawLine3D(p1[0], p1[1], p1[2], p2[0], p2[1], p2[2], color);
        }
    }
    
    /**
     * @return La imagen en memoria
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return pixeles de ancho
     */
    public int getWidth() {
		return width;
	}
    
    /**
     * @return pixeles de alto
     */
    public int getHeight() {
		return height;
	}
}
