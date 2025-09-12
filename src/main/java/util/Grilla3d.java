package util;

import java.util.ArrayList;

public class Grilla3d {
    private final ArrayList<ArrayList<ArrayList<Object>>> layers;

    public Grilla3d(int layerCount, int rowCount, int colCount) {
        this.layers = generateLayers(layerCount, rowCount, colCount);
    }

    private static ArrayList<ArrayList<ArrayList<Object>>> generateLayers(int layerCount, int rowCount, int colCount) {
        assert rowCount > 0 && layerCount > 0 && colCount > 0;

        ArrayList<ArrayList<ArrayList<Object>>> layers = new ArrayList<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            layers.add(generateLayer(rowCount, colCount));
        }
        layers.trimToSize();

        return layers;
    }

    private static ArrayList<ArrayList<Object>> generateLayer(int rowCount, int colCount) {
        assert rowCount > 0 && colCount > 0;

        ArrayList<ArrayList<Object>> layer = new ArrayList<>(rowCount);
        for (int i = 0; i < rowCount; i++) {
            layer.add(generateRow(colCount));
        }
        layer.trimToSize();

        return layer;
    }

    private static ArrayList<Object> generateRow(int colCount) {
        assert colCount > 0;

        ArrayList<Object> row = new ArrayList<>();
        for (int i = 0; i < colCount; i++) {
            row.add(null);
        }
        row.trimToSize();

        return row;
    }

    public Object get(int layer, int row, int col) {
        assert layer > 0 && layer < getLayerCount();
        assert row >= 0 && row < getWidth();
        assert col >= 0 && col < getHeight();

        return layers.get(layer).get(row).get(col);
    }

    public int getLayerCount() {
        return layers.size();
    }

    public int getWidth() {
        return layers.getFirst().size();
    }

    public int getHeight() {
        return layers.getFirst().getFirst().size();
    }
}
