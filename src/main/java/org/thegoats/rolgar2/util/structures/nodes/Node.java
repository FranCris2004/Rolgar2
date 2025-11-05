package org.thegoats.rolgar2.util.structures.nodes;

public abstract class Node<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private T data;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	Node(T data) {
		this.data = data;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public boolean hasData() {
		return this.data != null;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: devuelve el dato del NodoSimplementeEnlazado
	 */
	public T getData() {
		return this.data;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * pre:
	 * pos: devuelve el siguiente NodoSimplementeEnlazado
	 */
	public void setData(T data) {
		this.data = data;
	}
}
