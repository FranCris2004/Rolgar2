package org.thegoats.rolgar2.util.structures.nodes;


public class LinkedNode<T> extends Node<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private LinkedNode<T> next = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	public LinkedNode(T data) {
		super(data);
		this.next = null;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: el NodoSimplementeEnlazado resulta inicializado con el dato dado
     *       y sin un NodoSimplementeEnlazado siguiente.
	 */
	public boolean hasNext() {
		return this.next != null;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: cambia el dato del NodoSimplementeEnlazado
	 */
	public LinkedNode<T> getNext() {
		return this.next;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/**
	 * pre:
	 * pos: cambia el NodoSimplementeEnlazado siguiente
	 */
	public void setNext(LinkedNode<T> next) {
		this.next = next;
	}
}
