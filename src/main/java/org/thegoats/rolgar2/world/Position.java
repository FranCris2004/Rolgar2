package org.thegoats.rolgar2.world;

/**
 * Representa una posicion en el mundo
 * @param row No negativo
 * @param column No negativo
 * @param layer No negativo
 */
public record Position(int row, int column, int layer) {
    public Position {
        validateRow(row);
        validateColumn(column);
        validateLayer(layer);
    }

    /**
     * @param row No negativo
     */
    private static void validateRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("'row' debe ser positivo o cero");
        }
    }

    /**
     * @param column No negativo
     */
    private static void validateColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("'column' debe ser positivo o cero");
        }
    }

    /**
     * @param layer No negativo
     */
    public static void validateLayer(int layer) {
        if (layer < 0) {
            throw new IllegalArgumentException("'layer' debe ser positivo o cero");
        }
    }
}
