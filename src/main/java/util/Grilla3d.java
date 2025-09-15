package util;

import java.util.ArrayList;

public class Grilla3d {
    private final ArrayList<ArrayList<ArrayList<Object>>> grilla;

    public Grilla3d(int layerCount, int rowCount, int colCount) {
        this.grilla = generarGrilla(layerCount, rowCount, colCount);
    }

    private static ArrayList<ArrayList<ArrayList<Object>>> generarGrilla(int layerCount, int rowCount, int colCount) {
        assert rowCount > 0 && layerCount > 0 && colCount > 0;

        ArrayList<ArrayList<ArrayList<Object>>> layers = new ArrayList<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            layers.add(generarCapa(rowCount, colCount));
        }
        layers.trimToSize();

        return layers;
    }

    private static ArrayList<ArrayList<Object>> generarCapa(int numeroDeFilas, int numeroDeColumnas) {
        assert numeroDeFilas > 0 && numeroDeColumnas > 0;

        ArrayList<ArrayList<Object>> layer = new ArrayList<>(numeroDeFilas);
        for (int i = 0; i < numeroDeFilas; i++) {
            layer.add(generarFila(numeroDeColumnas));
        }
        layer.trimToSize();

        return layer;
    }

    private static ArrayList<Object> generarFila(int numeroDeColumnas) {
        assert numeroDeColumnas > 0;

        ArrayList<Object> row = new ArrayList<>();
        for (int i = 0; i < numeroDeColumnas; i++) {
            row.add(null);
        }
        row.trimToSize();

        return row;
    }

    public Object get(int indiceCapa, int indiceFila, int indiceColumna) {
        assert indiceCapa >= 0 && indiceCapa < getNumeroDeCapas();
        assert indiceFila >= 0 && indiceFila < getNumeroDeFilas();
        assert indiceColumna >= 0 && indiceColumna < getNumeroDeColumnas();

        return grilla.get(indiceCapa).get(indiceFila).get(indiceColumna);
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
