package org.thegoats.rolgar2.entidad;

import org.thegoats.rolgar2.mundo.Posicion;

/**
 * Representa un objeto con posicion en el mundo
 * ejemplos: personajes, cartas, trampas, etc
 */
public class Entidad {
    public org.thegoats.rolgar2.mundo.Posicion posicion;

    /**
     * @param posicionInicial No null
     */
    public Entidad(Posicion posicionInicial) {
        setPosicion(posicionInicial);
    }

    /**
     * @param posicion No null
     */
    public void setPosicion(Posicion posicion) {
        if (posicion == null) {
            throw new NullPointerException("'posicion' no debe ser null");
        }

        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }
}
