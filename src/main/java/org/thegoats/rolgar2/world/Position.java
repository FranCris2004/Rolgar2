package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.util.Assert;

/**
 * Representa una posicion en el mundo
 */
public final class Position {
    private int row;
    private int column;
    private int layer;

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

    //
    // Getters simples
    //

    /**
     * @return Fila
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Columna
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return Capa
     */
    public int getLayer() {
        return layer;
    }

    //
    // Setters simples
    //

    /**
     * @param row No negativo
     */
    public void setRow(int row) {
        Assert.nonNegative(row, "'row' debe ser positivo o cero");
        this.row = row;
    }

    /**
     * @param column No negativo
     */
    public void setColumn(int column) {
        Assert.nonNegative(column, "'column' debe ser positivo o cero");
        this.column = column;
    }

    /**
     * @param layer No negativo
     */
    public void setLayer(int layer) {
        Assert.nonNegative(layer, "'layer' debe ser positivo o cero");
        this.layer = layer;
    }

    /**
     * @param obj Position a comparar con la posicion invocadora.
     * @return true si la posicion invocadora y obj tienen las mismas componentes;
     * false si obj es null, son de distintas clases o tienen distintas componentes
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        Position other = (Position) obj;
        return this == obj
                || row == other.getRow()
                && layer == other.getLayer()
                && column == other.getColumn();
    }

    /**
     * @return La posicion en formato string
     */
    @Override
    public String toString(){
        return String.format(
                "Position[row=%d, column=%d, layer=%d]",
                row, column, layer
        );
    }

    /**
     * Comprueba que dos posiciones sean adyacentes (se encuentra a un casillero de distancia)
     * @param other No null
     * @return true si 'this' y 'other' son adyacentes, false si no lo son
     */
    public boolean isAdjacent(Position other) {
        Assert.notNull(other, "'other' debe ser no null");

        if (this.equals(other)) {
            return false;
        }

        // abs es el valor absoluto
        int dx = Math.abs(row - other.row);
        int dy = Math.abs(column - other.column);
        int dz = Math.abs(layer - other.layer);

        // this y other son adyacentes si todas las diferencias están en [-1, 1]
        return dx <= 1 && dy <= 1 && dz <= 1;
    }

    /**
     * No es necesario implementar equals, toString y hashCode
     * ya que el compilador implementa automaticamente estas
     * tres funciones en todos los records
     */
}
