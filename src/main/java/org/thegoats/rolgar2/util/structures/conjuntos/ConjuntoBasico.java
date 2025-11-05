package org.thegoats.rolgar2.util.structures.conjuntos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConjuntoBasico<E> {

    private List<E> elementos;

    public ConjuntoBasico() {
        this.elementos = new ArrayList<>();
    }


    public boolean agregar(E elemento) {
        if (!elementos.contains(elemento)) {
            elementos.add(elemento);
            return true;
        }
        return false;
    }

    public boolean quitar(E elemento) {
        return elementos.remove(elemento);
    }

    public boolean contiene(E elemento) {
        return elementos.contains(elemento);
    }

    public int tamaño() {
        return elementos.size();
    }

    public boolean esVacio() {
        return elementos.isEmpty();
    }

    public void vaciar() {
        elementos.clear();
    }

    public ConjuntoBasico<E> union(ConjuntoBasico<E> otro) {
    	ConjuntoBasico<E> resultado = new ConjuntoBasico<>();
        for (E e : this.elementos) {
            resultado.agregar(e);
        }
        for (E e : otro.elementos()) {
            resultado.agregar(e);
        }
        return resultado;
    }

    public ConjuntoBasico<E> interseccion(ConjuntoBasico<E> otro) {
    	ConjuntoBasico<E> resultado = new ConjuntoBasico<>();
        for (E e : this.elementos) {
            if (otro.contiene(e)) {
                resultado.agregar(e);
            }
        }
        return resultado;
    }

    public ConjuntoBasico<E> diferencia(ConjuntoBasico<E> otro) {
    	ConjuntoBasico<E> resultado = new ConjuntoBasico<>();
        for (E e : this.elementos) {
            if (!otro.contiene(e)) {
                resultado.agregar(e);
            }
        }
        return resultado;
    }

    public Collection<E> elementos() {
        return new ArrayList<>(elementos);
    }
}