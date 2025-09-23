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
        return position.getRow() >= 0 && position.getRow() < grid.getRowCount()
                && position.getColumn() >= 0 && position.getColumn() < grid.getColumnCount()
                && position.getLayer() >= 0 && position.getLayer() < grid.getLayerCount();
    }


    /**
     *
     * @param position no null
     * @return devuelve true si esta ocupada la celda, false caso contrario
     */
    public boolean isFilled(Position position) {
        Object contenido = grid.get(
                position.getRow(),
                position.getColumn(),
                position.getLayer()
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
     * mueve una entidad que se le de a la ubicacion que se le de
     * @param entidad no null
     * @param blockDestino no null
     */
    public void moverEntidad(Entity entidad,Block blockDestino) {
        Position newPosition = blockDestino.position();
        if(isInTheLimits(newPosition) && !isFilled(newPosition)) {
            if(blockDestino.isWalkable()){
                Object destino = grid.get(newPosition.row(), newPosition.column(), newPosition.layer());

                /*
                grid.set(entidad.getPosition().row(),entidad.getPosition().column(),entidad.getPosition().layer(), null);
                entidad.setPosition(newPosition);
                grid.set(newPosition.row(), newPosition.column(), newPosition.layer(), entidad);
                */

            }
            else{
                System.out.println("El Bloque no es caminable");
            }

        }
        else{
            System.out.println("No la nueva posicion no es valida");
        }


    }

    /**
     * elimina entidad de la grilla
     * @param entidad no null
     */
    public void eliminarEntidad(Entity entidad) {
        Position pos = entidad.getPosition();

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