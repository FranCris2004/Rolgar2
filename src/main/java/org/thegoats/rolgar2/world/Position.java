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
     * No es necesario implementar equals, toString y hashCode
     * ya que el compilador implementa automaticamente estas
     * tres funciones en todos los records
     */
}
