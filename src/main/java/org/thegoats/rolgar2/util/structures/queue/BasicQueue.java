package org.thegoats.rolgar2.util.structures.queue;

import java.util.List;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;

public class BasicQueue<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private LinkedNode<T> front = null;

	private LinkedNode<T> last = null;

	private int size = 0;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la cola vacia para su uso
	 */
	public BasicQueue() {
		this.front = null;
		this.last = null;
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
	 * post: agrega el elemento a la cola
	 */
	public void getInQueue(T element) {
        LinkedNode<T> temporary = new LinkedNode<T>(element);
		if (!this.isEmpty()) {
			this.last.setNext(temporary);
			this.last = temporary;
		} else {
			this.front = temporary;
			this.last = temporary;
		}
		this.size++;
	}

	/*
	 * pre: el elemento no es vacio
	 * post: agrega el elemento a la cola
	 */
	void getInQueue(List<T> list) {
		//validar
		for(T data: list) {
			this.getInQueue( data );
		}
	}

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola quitandolo.
	 */
	public T removeFromQueue() {
		T element = null;
		if (!this.isEmpty()) {
			element = this.front.getData();
			this.front = this.front.getNext();
			this.size--;
			if (isEmpty()) {
				this.last = null;
			}
		}
		return element;
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int countElement() {

		return this.size;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola. Solo lectura
	 */
	public T get() {
		T element = null;
		if (!this.isEmpty()) {
			element = this.front.getData();
		}
		return element;
	}

	public void getAllInQueue(List<T> list) {
		for(T item: list) {
			this.getInQueue(item);
		}		
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}