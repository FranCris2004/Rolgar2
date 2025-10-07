package org.thegoats.rolgar2.util;

import org.thegoats.rolgar2.world.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * Grilla o matriz en tres dimensiones
 */
public class Board3d<T> {
    private final List<List<List<T>>> grid;

    public Board3d(int rowCount, int columnCount, int layerCount) {
        this.grid = generateGrid(layerCount, rowCount, columnCount);
    }

    /**
     * Genera un grilla de numeroDeFilas x numeroDeColumnas x numeroDeCapas
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @return la grilla generada
     */
    private List<List<List<T>>> generateGrid(int rowCount, int columnCount, int layerCount) {
        Assert.positive(rowCount, "'rowCount' debe ser positivo");
        Assert.positive(columnCount, "'columnCount' debe ser positivo");
        Assert.positive(layerCount, "'layerCount' debe ser positivo");

        List<List<List<T>>> layers = new LinkedList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(generateLayer(rowCount, columnCount));
        }

        return layers;
    }

    /**
     * Genera una capa, de una grilla, de numeroDeFilas x numeroDeColumnas
     *
     * @param rowCount    Positivo
     * @param columnCount Positivo
     * @return la capa generada
     */
    private List<List<T>> generateLayer(int rowCount, int columnCount) {
        Assert.positive(rowCount, "'rowCount' debe ser positivo");
        Assert.positive(columnCount, "'columnCount' debe ser positivo");

        List<List<T>> layer = new LinkedList<>();
        for (int i = 0; i < rowCount; i++) {
            layer.add(generateRow(columnCount));
        }

        return layer;
    }

    /**
     * Genera una fila, de una capa, de una grilla, de numeroDeColumnas
     * @param columnCount Positivo
     * @return la fila generada
     */
    private List<T> generateRow(int columnCount) {
        Assert.positive(columnCount, "'columnCount' debe ser positivo");

        List<T> row = new LinkedList<>();
        for (int i = 0; i < columnCount; i++) {
            row.add(null);
        }

        return row;
    }

    /**
     * Obtiene un objeto en la grilla
     * @param row Numero de fila, mayor o igual a cero y menor a getRowCount()
     * @param column Numero de columna, mayor o cero y menor a igual a getColumnCount()
     * @param layer Numero de capa, mayor o igual a cero y menor a getLayerCount()
     * @return el objeto en la posición capa, fila, columna
     */
    public T get(int row, int column, int layer) {
        Assert.inRange(row, 0, getRowCount(), "'row' debe ser mayor o igual a cero y menor a getRowCount()");
        Assert.inRange(column, 0, getColumnCount(), "'column' debe ser mayor o cero y menor a igual a getColumnCount()");
        Assert.inRange(layer, 0, getLayerCount(), "'layer' debe ser mayor o igual a cero y menor a getLayerCount()");
        return grid.get(layer).get(row).get(column);
    }

    /**
     * Obtiene un objeto en la posicion 'position' de la grilla
     * @param position No null, debe ser una posicion valida
     * @return El objeto en la posicion
     */
    public T get(Position position) {
        Assert.notNull(position, "'position' debe ser no null");
        return get(position.row(), position.column(), position.layer());
    }

    /**
     * Pone value en una celda
     * @param row fila de la celda
     * @param column columna de la celda
     * @param layer capa de la celda
     * @param value valor de la celda
     */
    public void set(int row, int column, int layer, T value) {
        if (!isValidPosition(row, column, layer)) {
            throw new RuntimeException(new Position(row, column, layer) + " no es una posicion valida");
        }

        this.grid.get(row).get(column).set(layer, value);
    }

    /**
     * Pone value en una celda
     * @param position posicion de la celda
     * @param value valor de la celda
     */
    public void set(Position position, T value) {
        Assert.notNull(position, "'position' debe ser no null");
        set(position.row(), position.column(), position.layer(), value);
    }

    /**
     * @return numero de filas
     */
    public int getRowCount() {
        return grid.getFirst().size();
    }

    /**
     * @return numero de columnas
     */
    public int getColumnCount() {
        return grid.getFirst().getFirst().size();
    }

    /**
     * @return numero de capas
     */
    public int getLayerCount() {
        return grid.size();
    }

    /**
     * @return true si la posicion es valida, false si no lo es
     */
    public boolean isValidPosition(int row, int column, int layer) {
        return row >= 0 && row < getRowCount()
                && column >= 0 && column < getColumnCount()
                && layer >= 0 && layer < getLayerCount();
    }

    /**
     * @return true si la posicion es valida, false si no lo es o si es null
     */
    public boolean isValidPosition(Position position) {
        return position != null
                && isValidPosition(position.row(), position.column(), position.layer());
    }
}
