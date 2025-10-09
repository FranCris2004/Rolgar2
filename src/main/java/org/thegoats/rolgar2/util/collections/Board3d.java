package org.thegoats.rolgar2.util.collections;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Tablero de tres dimensiones
 */
public class Board3d<T> implements Iterable<T> {
    private final List<List<List<T>>> board;

    public Board3d(int rowCount, int columnCount, int layerCount) {
        board = generateBoard(rowCount, columnCount, layerCount);
    }

    //
    // Getters
    //

    /**
     * @return numero de filas
     */
    public int getRowCount() {
        return board.getFirst().size();
    }

    /**
     * @return numero de columnas
     */
    public int getColumnCount() {
        return board.getFirst().getFirst().size();
    }

    /**
     * @return numero de capas
     */
    public int getLayerCount() {
        return board.size();
    }


    /**
     * Obtiene un objeto en el tablero
     * @param row Numero de fila, mayor o igual a cero y menor a getRowCount()
     * @param column Numero de columna, mayor o cero y menor a igual a getColumnCount()
     * @param layer Numero de capa, mayor o igual a cero y menor a getLayerCount()
     * @return el objeto en la posición capa, fila, columna
     */
    public T get(int row, int column, int layer) {
        Assert.inRange(row, 0, getRowCount(), "'row' debe ser mayor o igual a cero y menor a getRowCount()");
        Assert.inRange(column, 0, getColumnCount(), "'column' debe ser mayor o cero y menor a igual a getColumnCount()");
        Assert.inRange(layer, 0, getLayerCount(), "'layer' debe ser mayor o igual a cero y menor a getLayerCount()");
        return board.get(layer).get(row).get(column);
    }

    /**
     * Obtiene un objeto en la posicion 'position' de el tablero
     * @param position No null, debe ser una posicion valida
     * @return El objeto en la posicion
     */
    public T get(Position position) {
        Assert.notNull(position, "'position' debe ser no null");
        return get(position.getLayer(), position.getRow(), position.getRow());
    }

    //
    // Setters
    //

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

        board.get(layer).get(row).set(column, value);
    }

    /**
     * Pone value en una celda
     * @param position posicion de la celda
     * @param value valor de la celda
     */
    public void set(Position position, T value) {
        Assert.notNull(position, "'position' debe ser no null");
        set(position.getRow(), position.getColumn(), position.getLayer(), value);
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
                && isValidPosition(position.getRow(), position.getColumn(), position.getLayer());
    }

    /**
     * <b>ADVERTENCIA</b>: Funcion pensada para ser utilizada por un Iterator por optimizacion, <b>NO DEBE SER UTILIZADA CON OTRO PROPOSITO</b>
     * @return La estructura de datos interna del tablero.
     */
    public List<List<List<T>>> _getBoard() {
        return board;
    }

    //
    // Helpers
    //

    /**
     * Genera un tablero de numeroDeFilas x numeroDeColumnas x numeroDeCapas
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @return el tablero generada
     */
    private List<List<List<T>>> generateBoard(int rowCount, int columnCount, int layerCount) {
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
     * Genera una capa, de un tablero, de numeroDeFilas x numeroDeColumnas
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
     * Genera una fila, de una capa, de un tablero, de numeroDeColumnas
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

    @Override
    public Iterator<T> iterator() {
        return new Board3dLinearIterator<>(List.copyOf(board));
    }
}
