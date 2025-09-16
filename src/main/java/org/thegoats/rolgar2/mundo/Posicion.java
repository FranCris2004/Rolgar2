package org.thegoats.rolgar2.mundo;

/**
 * Representa una posicion en el mundo
 */
public class Posicion {
    private int fila, columna, capa;

    /**
     * Equivalente a Posicion(0, 0, 0)
     */
    public Posicion() {
        this.fila = 0;
        this.columna = 0;
        this.capa = 0;
    }

    /**
     * @param fila No negativo
     * @param columna No negativo
     * @param capa No negativo
     */
    public Posicion(int fila, int columna, int capa) {
        setFila(fila);
        setColumna(columna);
        setCapa(capa);
    }

    /**
     * @param fila No negativo
     */
    public void setFila(int fila) {
        if (fila < 0) {
            throw new IllegalArgumentException("'x' debe ser positivo o cero");
        }

        this.fila = fila;
    }

    /**
     * @param columna No negativo
     */
    public void setColumna(int columna) {
        if (fila < 0) {
            throw new IllegalArgumentException("'y' debe ser positivo o cero");
        }

        this.columna = columna;
    }

    /**
     * @param capa No negativo
     */
    public void setCapa(int capa) {
        if (fila < 0) {
            throw new IllegalArgumentException("'z' debe ser positivo o cero");
        }

        this.capa = capa;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getCapa() {
        return capa;
    }
}
