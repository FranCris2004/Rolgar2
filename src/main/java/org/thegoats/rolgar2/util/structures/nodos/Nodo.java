package org.thegoats.rolgar2.util.structures.nodos;

public abstract class Nodo<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private T dato;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	Nodo(T dato) {
		this.dato = dato;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public boolean tieneDato() {
		return this.dato != null;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: devuelve el dato del NodoSimplementeEnlazado
	 */
	public T getDato() {
		return this.dato;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * pre:
	 * pos: devuelve el siguiente NodoSimplementeEnlazado
	 */
	public void setDato(T dato) {
		this.dato = dato;
	}
}
