package org.thegoats.rolgar2.mundo;

import org.thegoats.rolgar2.entity.Entity;
import org.thegoats.rolgar2.util.Grid3d;
import org.thegoats.rolgar2.world.Block;
import org.thegoats.rolgar2.world.Position;

public class Mundo {


    //INTERFACES ----------------------------------------------------------------------------------------------
//ENUMERADOS ----------------------------------------------------------------------------------------------
//CONSTANTES ----------------------------------------------------------------------------------------------
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private Grid3d grilla3d;


    //ATRIBUTOS -----------------------------------------------------------------------------------------------
//ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     *
     * @param filas no negativo
     * @param columnas no negativo
     * @param capas no negativo
     */
    public Mundo(int filas, int columnas, int capas) {
        this.grilla3d = new Grid3d(filas, columnas, capas);
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
    public boolean estaEnLosLimites(Position position) {
        return position.getRow() >= 0 && position.getRow() < grilla3d.getRowCount()
                && position.getColumn() >= 0 && position.getColumn() < grilla3d.getColumnCount()
                && position.getLayer() >= 0 && position.getLayer() < grilla3d.getLayerCount();
    }


    /**
     *
     * @param position no null
     * @return devuelve true si esta ocupada la celda, false caso contrario
     */
    public boolean esCeldaOcupada(Position position) {
        Object contenido = grilla3d.get(
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
     * @param entidad no null
     * @param block no null
     */
    public void agregarEntidad(Entity entidad, Block block) {
        Position posicion = block.getPosition();
            if (block.getIsWalkable()){
                if(estaEnLosLimites(block.getPosition()) && !esCeldaOcupada(posicion)) {
                    grilla3d.set(
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


    }


    /**
     * mueve una entidad que se le de a la ubicacion que se le de
     * @param entidad no null
     * @param blockDestino no null
     */
    public void moverEntidad(Entity entidad,Block blockDestino) {
        Position newPosition = blockDestino.getPosition();
        if(estaEnLosLimites(newPosition) && !esCeldaOcupada(newPosition)) {
            if(blockDestino.getIsWalkable()){
                Object destino = grilla3d.get(newPosition.getRow(), newPosition.getColumn(), newPosition.getLayer());

                grilla3d.set(entidad.getPosition().getRow(),entidad.getPosition().getColumn(),entidad.getPosition().getLayer(), null);
                entidad.setPosition(newPosition);
                grilla3d.set(newPosition.getRow(), newPosition.getColumn(), newPosition.getLayer(), entidad);


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
        grilla3d.set(pos.getRow(), pos.getColumn(), pos.getLayer(), null);
    }



//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------


    //SETTERS COMPLEJOS----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------











}