package util;

import java.util.ArrayList;

/**
 * Grilla o matriz en tres dimensiones
 */
public class Grilla3d {
    private final ArrayList<ArrayList<ArrayList<Object>>> grilla;

    public Grilla3d(int numeroDeFilas, int numeroDeColumnas, int numeroDeCapas) {
        this.grilla = generarGrilla(numeroDeCapas, numeroDeFilas, numeroDeColumnas);
    }

    /**
     * Genera un grilla de numeroDeFilas x numeroDeColumnas x numeroDeCapas
     * @param numeroDeFilas
     * @param numeroDeColumnas
     * @param numeroDeCapas
     * @return la grilla generada
     */
    private static ArrayList<ArrayList<ArrayList<Object>>> generarGrilla(int numeroDeFilas, int numeroDeColumnas, int numeroDeCapas) {
        assert numeroDeFilas > 0 && numeroDeCapas > 0 && numeroDeColumnas > 0;

        ArrayList<ArrayList<ArrayList<Object>>> layers = new ArrayList<>(numeroDeCapas);
        for (int i = 0; i < numeroDeCapas; i++) {
            layers.add(generarCapa(numeroDeFilas, numeroDeColumnas));
        }
        layers.trimToSize();

        return layers;
    }

    /**
     * Genera una capa, de una grilla, de numeroDeFilas x numeroDeColumnas
     * @param numeroDeFilas
     * @param numeroDeColumnas
     * @return la capa generada
     */
    private static ArrayList<ArrayList<Object>> generarCapa(int numeroDeFilas, int numeroDeColumnas) {
        assert numeroDeFilas > 0 && numeroDeColumnas > 0;

        ArrayList<ArrayList<Object>> layer = new ArrayList<>(numeroDeFilas);
        for (int i = 0; i < numeroDeFilas; i++) {
            layer.add(generarFila(numeroDeColumnas));
        }
        layer.trimToSize();

        return layer;
    }

    /**
     * Genera una fila, de una capa, de una grilla, de numeroDeColumnas
     * @param numeroDeColumnas
     * @return la fila generada
     */
    private static ArrayList<Object> generarFila(int numeroDeColumnas) {
        assert numeroDeColumnas > 0;

        ArrayList<Object> row = new ArrayList<>();
        for (int i = 0; i < numeroDeColumnas; i++) {
            row.add(null);
        }
        row.trimToSize();

        return row;
    }

    /**
     * Obtiene un objeto en la grilla
     * @param capa Numero de capa
     * @param fila Numero de fila
     * @param columna Numero de columna
     * @return el objeto en la posición capa, fila, columna
     */
    public Object get(int capa, int fila, int columna) {
        assert capa >= 0 && capa < getNumeroDeCapas();
        assert fila >= 0 && fila < getNumeroDeFilas();
        assert columna >= 0 && columna < getNumeroDeColumnas();

        return grilla.get(capa).get(fila).get(columna);
    }

    public int getNumeroDeCapas() {
        return grilla.size();
    }

    public int getNumeroDeFilas() {
        return grilla.getFirst().size();
    }

    public int getNumeroDeColumnas() {
        return grilla.getFirst().getFirst().size();
    }
}
