package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.entity.Entity;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Grid3d;

public class World {


    //INTERFACES ----------------------------------------------------------------------------------------------
//ENUMERADOS ----------------------------------------------------------------------------------------------
//CONSTANTES ----------------------------------------------------------------------------------------------
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private Grid3d grid;


    //ATRIBUTOS -----------------------------------------------------------------------------------------------
//ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     *
     * @param rows no negativo
     * @param columns no negativo
     * @param layers no negativo
     */
    public World(int rows, int columns, int layers) {
        this.grid = new Grid3d(rows, columns, layers);
    }

    //METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS HEREDADOS (CLASE)--------------------------------------------------------------------------------
//METODOS HEREDADOS (INTERFACE)----------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    /**
     *
     * @param position
     * @return devuelve falso si se va del limite, true si se mantiene en el limite
     */
    public boolean isInTheLimits(Position position) {
        return position.row() >= 0 && position.row() < grid.getRowCount()
                && position.column() >= 0 && position.column() < grid.getColumnCount()
                && position.layer() >= 0 && position.layer() < grid.getLayerCount();
    }

    /**
     *
     * @param position no null
     * @return devuelve true si esta ocupada la celda, false caso contrario
     */
    public boolean isFilled(Position position) {
        Object contenido = grid.get(
                position.row(),
                position.column(),
                position.layer()
        );

        if (contenido != null) {
            return true;
        }

        return false;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * agrega una entidad que se le de en la posicion del bloque que se le de
     * @param entity no null
     * @param position no null
     */
    public void addEntity(Entity entity,Position position) {
        Assert.notNull(position, "'position' debe ser no nula");
        Assert.notNull(entity, "'entity' debe ser no nula");
        Assert.isNull(this.grid.get(position),"La posicion no esta vacia");
        Object underCell = this.grid.get(position.row(), position.column(), position.layer() - 1);

        Assert.notNull(underCell, "'underCell' debe ser no nula");

        if (!(underCell instanceof Block)) {
            throw new RuntimeException("La celda de abajo no es un bloque");
        }
        if(!((Block) underCell).isWalkable()) {
            throw new RuntimeException("El bloque de abajo no es caminable");
        }
        this.grid[position.row()][position.column()][position.layer()] = entity;

    }


    /**
     * mueve entity hacia position
     * @param entity no null
     * @param position no null
     */
    public void moveEntity(Entity entity,Position position) {
        Assert.notNull(position, "'position' debe ser no nula");
        Assert.notNull(entity, "'entity' debe ser no nula");
        Assert.isNull(this.grid.get(position),"La posicion no esta vacia");
        Object underCell = this.grid.get(position.row(), position.column(), position.layer() - 1);

        Assert.notNull(underCell, "'underCell' debe ser no nula");

        if (!(underCell instanceof Block)) {
            throw new RuntimeException("La celda de abajo no es un bloque");
        }
        if(!((Block) underCell).isWalkable()) {
            throw new RuntimeException("El bloque de abajo no es caminable");
        }
    }

    /**
     * elimina entity de la grilla
     * @param entity no null
     */
    public void deleteEntity(Entity entity) {
        Position pos = entity.getPosition();

        /*
        grid.set(pos.row(), pos.column(), pos.layer(), null);
         */
    }

//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS COMPLEJOS----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}