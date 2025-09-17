package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.util.Assert;

/**
 * Representa una posicion en el mundo
 * @param row No negativo
 * @param column No negativo
 * @param layer No negativo
 */
public record Position(int row, int column, int layer) {
    public Position {
        Assert.nonNegative(row, "'row' debe ser positivo o cero");
        Assert.nonNegative(column, "'column' debe ser positivo o cero");
        Assert.nonNegative(layer, "'layer' debe ser positivo o cero");
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
