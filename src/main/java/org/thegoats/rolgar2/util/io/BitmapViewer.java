package org.thegoats.rolgar2.util.io;

import org.thegoats.rolgar2.util.Assert;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BitmapViewer {
    private JFrame frame;
    private JPanel panel;
    private List<Bitmap> bitmaps;
    
    /**
     * Genera un visualizador de bitmaps con los bitmaps adjuntos
     * @param bitmaps
     */
    private BitmapViewer(List<Bitmap> bitmaps) {
    	Assert.notNull(bitmaps, "bitmaps");
    	Assert.positive(bitmaps.size(), "bitmaps");
        this.bitmaps = bitmaps;
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    /**
     * Inicia el visualizador de bitmaps
     * @param bitmaps
     */
    public static void showBitmaps(Collection<Bitmap> bitmaps) {
        new BitmapViewer(bitmaps.stream().toList());
    }

    /**
     * Genera una grafica con los bitmaps
     * Luego los refresca cada 0.5 segundo
     */
    private void createAndShowGUI() {
        frame = new JFrame("Visualizador de Bitmaps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(new GridLayout(0, 3, 10, 10));

        for (Bitmap bmp : bitmaps) {
            JLabel label = new JLabel(new ImageIcon(bmp.getImage()));
            panel.add(label);
        }

        frame.add(new JScrollPane(panel));
        frame.setSize(1000, 800);
        frame.setVisible(true);

        // Refresca automáticamente cada 500 ms
        new Timer(500, e -> refreshImages()).start();
    }
    
    /**
     * Refresca las imagenes
     */
    private void refreshImages() {
        Component[] comps = panel.getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof JLabel lbl) {
                lbl.setIcon(new ImageIcon(bitmaps.get(i).getImage()));
            }
        }
    }
}