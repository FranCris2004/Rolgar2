package posicion;

public class Posicion {

    // Atributos
    int fila;
    int columna;
    int profundidad;

    // Constructor
    public Posicion(int fila, int columna, int profundidad) {
        this.fila = fila;
        this.columna = columna;
        this.profundidad = profundidad;
    }

    // Metodos
    public Posicion actualicePosition (int movimientoFila, int movimientoColumna, int movimientoProfundidad) {
        return new Posicion(this.fila + movimientoFila, this.columna + movimientoColumna, this.profundidad + movimientoProfundidad);
    }

    public boolean adentroDelCasillero(int maxFilas, int maxColumnas, int maxProfundidad) {
        if (this.fila >= 0 && this.columna >= 0 && this.profundidad >= 0 && this.fila < maxFilas && this.columna < maxColumnas && this.profundidad < maxProfundidad) {
            return true;
        } else {
            return false;
        }
    }


    // Getters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
