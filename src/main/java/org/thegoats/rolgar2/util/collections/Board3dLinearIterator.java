package org.thegoats.rolgar2.util.collections;

import org.thegoats.rolgar2.util.Assert;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Itera pasando por cada fila de cada columna de cada capa del tablero
 * @param <T> Tipo que contiene el tablero
 */
public class Board3dLinearIterator<T> implements Iterator<T> {
    private Iterator<List<List<T>>> layerIterator;
    private Iterator<List<T>> columnIterator;
    private Iterator<T> rowIterator;

    public Board3dLinearIterator(List<List<List<T>>> board) {
        Assert.notNull(board, "board debe ser no nulo");
        layerIterator = board.iterator();

        if (!layerIterator.hasNext()) {
            columnIterator = Collections.emptyIterator();
            rowIterator = Collections.emptyIterator();
            return;
        }

        columnIterator = layerIterator.next().iterator();

        if (!columnIterator.hasNext()) {
            rowIterator = Collections.emptyIterator();
            return;
        }

        rowIterator = columnIterator.next().iterator();
    }

    @Override
    public boolean hasNext() {
        if (!rowIterator.hasNext()) { // si no hay otro elemento en la fila
            if (!columnIterator.hasNext()) { // si no hay otra fila
                if (!layerIterator.hasNext()) { // si no hay otra capa
                    return false; // devuelve false
                }
                columnIterator = layerIterator.next().iterator(); // si hay otra capa avanza
            }
            rowIterator = columnIterator.next().iterator(); // si hay otra fila avanza
        }

        return rowIterator.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No quedan elementos en el tablero");
        }

        return rowIterator.next();
    }
}
