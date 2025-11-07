package org.thegoats.rolgar2.util.io;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BitmapDemo {
    public static void main(String[] args) throws Exception {
        List<Bitmap> bitmaps = new LinkedList<>();
        bitmaps.add(new Bitmap(200, 200));
        bitmaps.add(new Bitmap(400, 400));

        BitmapViewer.showBitmaps(bitmaps);

        bitmaps.get(0).drawCircle(100, 100, 50, Color.RED);
        bitmaps.get(1).drawCube(100, 100, 0, 0, Color.BLUE);

        // Actualización en tiempo real del bitmap y se dibuja automaticamente
        for (int i = 0; i < 200; i++) {
            bitmaps.get(0).drawText(i + "s", 50, 30, new Font("Arial", Font.BOLD, 28), Color.WHITE, Color.BLACK);
            Thread.sleep(10);
            bitmaps.get(1).drawPixel(i, i, Color.GREEN);
        }
    }
}
