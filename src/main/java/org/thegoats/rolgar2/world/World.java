package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.entity.Entity;
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

            Object underCell = this.grid.get(position.getRow(), position.getColumn(), position.getLayer()-1);

            if (underCell == null) {
                throw new RuntimeException("La celda de abajo es nula");
            }
            if (!(underCell instanceof Block)) {
                throw new RuntimeException("La celda de abajo no es un bloque");
            }
            if(!((Block) underCell).getIsWalkable()) {
                throw new RuntimeException("El bloque de abajo no es caminable");
            }



                /**
                 *

                if(isInTheLimits(block.getPosition()) && !isFilled(posicion)) {
                    grid.set(
                            posicion.getRow(),
                            posicion.getColumn(),
                            posicion.getLayer(),
                            entidad
                    );
                    entidad.setPosition(posicion);
                }
                else{
                    System.out.println("La posicion no es valida");
                }



            }
            else{
                System.out.println("el lugar donde quieres agregar la identidad no es caminable");
            }
            */

    }


    /**
     * mueve una entidad que se le de a la ubicacion que se le de
     * @param entidad no null
     * @param blockDestino no null
     */
    public void moverEntidad(Entity entidad,Block blockDestino) {
        Position newPosition = blockDestino.getPosition();
        if(isInTheLimits(newPosition) && !isFilled(newPosition)) {
            if(blockDestino.getIsWalkable()){
                Object destino = grid.get(newPosition.getRow(), newPosition.getColumn(), newPosition.getLayer());

                grid.set(entidad.getPosition().getRow(),entidad.getPosition().getColumn(),entidad.getPosition().getLayer(), null);
                entidad.setPosition(newPosition);
                grid.set(newPosition.getRow(), newPosition.getColumn(), newPosition.getLayer(), entidad);


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
        grid.set(pos.getRow(), pos.getColumn(), pos.getLayer(), null);
    }



//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------


    //SETTERS COMPLEJOS----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------











}