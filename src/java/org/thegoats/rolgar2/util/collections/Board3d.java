package org.thegoats.rolgar2.util.collections;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Tablero de tres dimensiones
 */
public class Board3d<T> implements Iterable<T> {
    private final List<List<List<T>>> board;

    /**
     * Genera un tablero de rowCount x columnCount x layerCount
     * usando la función proporcionada para crear cada elemento según su posición.
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @param function No nula, función que genera los elementos del tablero con base a su posición
     */
    public <U> Board3d(int rowCount, int columnCount, int layerCount, Function<Position, T> function) {
        Assert.positive(rowCount, "'rowCount' debe ser positivo");
        Assert.positive(columnCount, "'columnCount' debe ser positivo");
        Assert.positive(layerCount, "'layerCount' debe ser positivo");
        Assert.notNull(function, "'function' debe ser no nula");

        board = generateBoard(rowCount, columnCount, layerCount, function);
    }

    /**
     * Genera un tablero de rowCount x columnCount x layerCount
     * usando el supplier proporcionado para crear cada elemento
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @param supplier No nulo, supplier que genera los elementos del tablero
     */
    public Board3d(int rowCount, int columnCount, int layerCount, Supplier<T> supplier) {
        this(rowCount, columnCount, layerCount, (ignoredPosition) -> supplier.get());
    }

    /**
     * Genera un tablero de rowCount x columnCount x layerCount
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     */
    public Board3d(int rowCount, int columnCount, int layerCount) {
        this(rowCount, columnCount, layerCount, (ignoredPosition) -> null);
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

    //
    // Implementacion de Iterable
    //

    /**
     * Iterador que itera pasando por cada fila de cada columna de cada capa del tablero
     */
    @Override
    public Iterator<T> iterator() {
        return new Board3dLinearIterator<>(List.copyOf(board));
    }

    //
    // Helpers
    //

    /**
     * Genera la estructura interna de un tablero de rowCount x columnCount x layerCount
     * usando la función proporcionada para crear cada elemento según su posición.
     *
     * @param rowCount Positivo
     * @param columnCount Positivo
     * @param layerCount Positivo
     * @param function No nula, función que genera los elementos del tablero con base a su posición
     * @return El tablero generado
     */
    private List<List<List<T>>> generateBoard(int rowCount, int columnCount, int layerCount, Function<Position, T> function) {
        List<List<List<T>>> board = new LinkedList<>();
        for (int z = 0; z < layerCount; z++) {
            List<List<T>> layer = new LinkedList<>();
            for (int y = 0; y < rowCount; y++) {
                List<T> row = new LinkedList<>();
                for (int x = 0; x < columnCount; x++) {
                    row.add(function.apply(new Position(y, x, z)));
                }
                layer.add(row);
            }
            board.add(layer);
        }

        return board;
    }
}
