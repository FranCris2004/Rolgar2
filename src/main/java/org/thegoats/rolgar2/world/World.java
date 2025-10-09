package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.collections.Board3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que envuelve Board3d para adicionarle logica, validaciones y metodos propios del juego
 */
public class World implements Iterable<WorldCell> {
    //
    // Atributos privados
    //

    private final Board3d<WorldCell> board;

    //
    // Constructor
    //

    public World(int rows, int columns, int layers) {
        board = new Board3d<>(rows, columns, layers, WorldCell::new);
        initWorldCells();
    }

    //
    // Setters simples
    //

    public void setCell(Position position, CharacterData character) {
        board.get(position).set(character);
    }

    public void setCell(Position position, Block block) {
        board.get(position).set(block);
    }

    //
    // Getters simples
    //

    public int getRowCount() {
        return board.getRowCount();
    }

    public int getColumnCount() {
        return board.getColumnCount();
    }

    public int getLayerCount() {
        return board.getLayerCount();
    }

    public WorldCell getCell(int row, int column, int layer) {
        return board.get(row, column, layer);
    }

    public WorldCell getCell(Position position) {
        return board.get(position);
    }

    @Override
    public Iterator<WorldCell> iterator() {
        return board.iterator();
    }

    //
    // Helpers
    //

    private void initWorldCells() {
        List<WorldCell> neighbors = new ArrayList<>();

        for (WorldCell worldCell : board) {
            for (int dz = -1; dz <= 1; dz++) {
                int layer = worldCell.getPosition().getLayer() + dz;

                // salta la celda si esta fuera de los limites
                if (layer < 0 || layer >= getLayerCount())
                    continue;

                for (int dy = -1; dy <= 1; dy++) {
                    int column = worldCell.getPosition().getColumn() + dy;

                    // salta la celda si esta fuera de los limites
                    if (column < 0 || column >= getRowCount())
                        continue;

                    for (int dx = -1; dx <= 1; dx++) {
                        int row = worldCell.getPosition().getRow() + dx;

                        // salta la celda si esta fuera de los limites
                        if (row < 0 || row >= getColumnCount())
                            continue;

                        // saltar la celda actual
                        if (dx == 0 && dy == 0 && dz == 0)
                            continue;

                        neighbors.add(board.get(row, column, layer));
                    }
                }
            }

            worldCell.initNeighbors(neighbors);
        }
    }
}
