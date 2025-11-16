package org.thegoats.rolgar2.util.structures.nodes;

public class DoublyLinkedNode<T> extends LinkedNode<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private DoublyLinkedNode<T> previous = null;
	
	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre: -
	 * pos: el Nodo resulta inicializado con el dato dado
     *       y sin un Nodo siguiente.
	 */
	public DoublyLinkedNode(T data) {
		super(data);
		this.previous = null;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: devuelve el nodo anterior
	 */
	public DoublyLinkedNode<T> getPrevious() {
		return previous;
	}
	
	@Override
	public DoublyLinkedNode<T> getNext() {
		return (DoublyLinkedNode<T>) super.getNext();
	}
	
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * pre: -
	 * pos: cambia el nodo anterior
	 */
	public void setPrevious(DoublyLinkedNode<T> previous) {
		this.previous = previous;
	}

}