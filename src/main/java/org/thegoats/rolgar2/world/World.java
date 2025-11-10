package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.collections.Board3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase que envuelve Board3d para adicionarle logica, validaciones y metodos propios del juego
 */
public class World implements Iterable<WorldCell> {
    private final Board3d<WorldCell> board;

    /**
     * Crea un mundo de Rows.ColumnsxLayers
     * @param rows filas del tablero, mayor a cero
     * @param columns columnas del tablero, mayor a cero
     * @param layers capas del tablero, mayor a cero
     */
    public World(int rows, int columns, int layers) {
        board = new Board3d<>(rows, columns, layers, WorldCell::new);
        initWorldCells();
    }

    /**
     * @param position no nulo, dentro de los limites del mundo
     * @param character no nulo
     */
    public void setCell(Position position, CharacterData character) {
        Assert.notNull(character, "character debe ser no nulo");
        board.get(position).set(character);
    }

    /**
     * @param position no nulo, dentro de los limites del mundo
     * @param block no nulo
     */
    public void setCell(Position position, Block block) {
        Assert.notNull(block, "block debe ser no nulo");
        board.get(position).set(block);
    }

    //
    // Getters simples
    //

    /**
     * @return Cantidad de filas
     */
    public int getRowCount() {
        return board.getRowCount();
    }

    /**
     * @return Cantidad de columnas
     */
    public int getColumnCount() {
        return board.getColumnCount();
    }

    /**
     * @return Cantidad de capas
     */
    public int getLayerCount() {
        return board.getLayerCount();
    }

    /**
     * @param row fila, mayor o igual a cero y menor a getRowCount
     * @param column columna, mayor o igual a cero y menor a getColumnsCount
     * @param layer capa, mayor o igual a cero y menor a getLayerCount
     * @return La celda en la posicion {row, column, layer}
     */
    public WorldCell getCell(int row, int column, int layer) {
        return board.get(row, column, layer);
    }

    /**
     * @param position no nulo, posicion válida dentro del tablero
     * @return La celda en la posicion position
     */
    public WorldCell getCell(Position position) {
        return board.get(position);
    }

    /**
     * Obtiene una celda aleatoria vacia en la que se pueda caminar, es decir, que no contenga objetos
     * @param random generador aleatorio de valores
     * @return una celda vacia en la que se pueda caminar
     */
    public WorldCell getRandomEmptyWalkableCell(Random random) {
        Assert.notNull(random, "Random no puede ser null");
        List<WorldCell> emptyWalkableCells = new ArrayList<>();

        board.forEach(cell -> {
            var floor = cell.getFloor();
            if (cell.hasNull() && floor.hasBlock() && floor.getBlock().isWalkable) {
                emptyWalkableCells.add(cell);
            }
        });

        return emptyWalkableCells.get(random.nextInt(0, emptyWalkableCells.size()));
    }

    /**
     * Iterador que itera pasando por cada fila de cada columna de cada capa del tablero
     */
    @Override
    public Iterator<WorldCell> iterator() {
        return board.iterator();
    }

    //
    // Helpers
    //

    /**
     * Inicializa los vecinos de las celdas (26 vecinos)
     */
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
                    if (column < 0 || column >= getColumnCount())
                        continue;

                    for (int dx = -1; dx <= 1; dx++) {
                        int row = worldCell.getPosition().getRow() + dx;

                        // salta la celda si esta fuera de los limites
                        if (row < 0 || row >= getRowCount())
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
