package org.thegoats.rolgar2.world;

/**
 * Representa una posicion en el mundo
 */
public class Position {
    private int row, column, layer;

    /**
     * Equivalente a Posicion(0, 0, 0)
     */
    public Position() {
        this.row = 0;
        this.column = 0;
        this.layer = 0;
    }

    /**
     * @param row No negativo
     * @param column No negativo
     * @param layer No negativo
     */
    public Position(int row, int column, int layer) {
        setRow(row);
        setColumn(column);
        setLayer(layer);
    }

    /**
     * @param row No negativo
     */
    public void setRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("'row' debe ser positivo o cero");
        }

        this.row = row;
    }

    /**
     * @param column No negativo
     */
    public void setColumn(int column) {
        if (row < 0) {
            throw new IllegalArgumentException("'column' debe ser positivo o cero");
        }

        this.column = column;
    }

    /**
     * @param layer No negativo
     */
    public void setLayer(int layer) {
        if (row < 0) {
            throw new IllegalArgumentException("'layer' debe ser positivo o cero");
        }

        this.layer = layer;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getLayer() {
        return layer;
    }
}
