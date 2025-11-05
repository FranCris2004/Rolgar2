package org.thegoats.rolgar2.util.structures.nodos;


public class NodoSimplementeEnlazado<T> extends Nodo<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private NodoSimplementeEnlazado<T> siguiente = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	public NodoSimplementeEnlazado(T dato) {
		super(dato);
		this.siguiente = null;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	public boolean tieneSiguiente() {
		return this.siguiente != null;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: cambia el dato del NodoSimplementeEnlazado
	 */
	public NodoSimplementeEnlazado<T> getSiguiente() {
		return this.siguiente;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/**
	 * pre:
	 * pos: cambia el NodoSimplementeEnlazado siguiente
	 */
	public void setSiguiente(NodoSimplementeEnlazado<T> siguiente) {
		this.siguiente = siguiente;
	}
}
