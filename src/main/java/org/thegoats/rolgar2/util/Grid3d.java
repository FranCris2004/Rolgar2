package org.thegoats.rolgar2.util;

import java.util.ArrayList;

/**
 * Grilla o matriz en tres dimensiones
 */
public class Grid3d {
    private final ArrayList<ArrayList<ArrayList<Object>>> grid;

    public Grid3d(int rowCount, int columnCount, int layerCount) {
        this.grid = generateGrid(layerCount, rowCount, columnCount);
    }

    /**
     * Genera un grilla de numeroDeFilas x numeroDeColumnas x numeroDeCapas
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @return la grilla generada
     */
    private static ArrayList<ArrayList<ArrayList<Object>>> generateGrid(int rowCount, int columnCount, int layerCount) {
        assert rowCount > 0 && layerCount > 0 && columnCount > 0;

        ArrayList<ArrayList<ArrayList<Object>>> layers = new ArrayList<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            layers.add(generateLayer(rowCount, columnCount));
        }
        layers.trimToSize();

        return layers;
    }

    /**
     * Genera una capa, de una grilla, de numeroDeFilas x numeroDeColumnas
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @return la capa generada
     */
    private static ArrayList<ArrayList<Object>> generateLayer(int rowCount, int columnCount) {
        assert rowCount > 0 && columnCount > 0;

        ArrayList<ArrayList<Object>> layer = new ArrayList<>(rowCount);
        for (int i = 0; i < rowCount; i++) {
            layer.add(generateRow(columnCount));
        }
        layer.trimToSize();

        return layer;
    }

    /**
     * Genera una fila, de una capa, de una grilla, de numeroDeColumnas
     * @param columnCount Positivo
     * @return la fila generada
     */
    private static ArrayList<Object> generateRow(int columnCount) {
        assert columnCount > 0;

        ArrayList<Object> row = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            row.add(null);
        }
        row.trimToSize();

        return row;
    }

    /**
     * Obtiene un objeto en la grilla
     * @param row Numero de fila
     * @param column Numero de columna
     * @param layer Numero de capa
     * @return el objeto en la posición capa, fila, columna
     */
    public Object get(int row, int column, int layer) {
        assert row >= 0 && row < getRowCount();
        assert column >= 0 && column < getColumnCount();
        assert layer >= 0 && layer < getLayerCount();

        return grid.get(layer).get(row).get(column);
    }

    public int getRowCount() {
        return grid.getFirst().size();
    }

    public int getColumnCount() {
        return grid.getFirst().getFirst().size();
    }

    public int getLayerCount() {
        return grid.size();
    }
}
