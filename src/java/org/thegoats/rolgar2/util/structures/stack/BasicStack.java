package org.thegoats.rolgar2.util.structures.stack;

import java.util.List;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;

public class BasicStack<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private LinkedNode<T> top = null;
	private int size = 0;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la pila vacia para su uso
	 */
	public BasicStack() {
		this.top = null;
		this.size = 0;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	/*
	 * post: indica si la cola tiene algún elemento.
	 */
	public boolean isEmpty() {
		return (this.size == 0);
	}

	/*
	 * pre: el elemento no es vacio
	 * post: agrega el elemento a la pila
	 */
	public void getInStack(T element) {
        LinkedNode<T>temporary = new LinkedNode<T>(element);
		temporary.setNext(this.top);
		this.top = temporary;
		this.size++;
	}

	/*
	 * pre: el elemento no es vacio
	 * post: agrega el elemento a la pila
	 */
	public void getInStack(List<T> list) {
		//validar
		for(T data: list) {
			this.getInStack( data );
		}
	}

	/*
	 * pre :
	 * post: devuelve el elemento en el tope de la pila y achica la pila en 1.
	 */
	public T removeFromStack() {
		T element = null;
		if (!this.isEmpty()) {
			element = this.top.getData();
			this.top = this.top.getNext();
			this.size--;
		}
		return element;
	}

	/**
	 * pre: -
	 * post: devuelve el elemento en el tope de la pila (solo lectura)
	 */
	public T get() {
		T element = null;
		if (!this.isEmpty()) {
			element = this.top.getData();
		}
		return element;
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int countElements() {
		return this.size;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}