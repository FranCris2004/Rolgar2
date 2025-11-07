package org.thegoats.rolgar2.util.structures.lists;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;
import org.thegoats.rolgar2.util.Assert;

public class ListIterator<T extends Object> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private LinkedNode<T> first = null;
	private int length = 0;
	private LinkedNode<T> cursor = null;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: crea una lista vacia
	 */
	public ListIterator() {
		this.first = null;
		this.length = 0;
		this.cursor = null;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: indica si la Lista tiene algún elemento.
	 */
	public boolean isEmpty() {
		return (this.length == 0);
	}

	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
	public void add(T element) throws Exception {
		this.add(this.getLength() + 1, element);
	}

	/**
	 * pre: posición pertenece al intervalo: [1, contarElementos() + 1]
	 * pos: agrega el elemento en la posición indicada.
	 */
	public void add(int position, T element) throws Exception {
		Assert.inRange(position, 1, this.length, "position debe estar entre 1 y el largo de la lista");
		LinkedNode<T> temporary = new LinkedNode<T>(element);
		if (position == 1) {
			temporary.setNext( this.first);
			this.first = temporary;
		} else {
			LinkedNode<T> previous = this.getNode(position -1);
			temporary.setNext( previous.getNext());
			previous.setNext( temporary );
		}
		this.length++;
	}

	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: remueve de la Lista el elemento en la posición indicada.
	 */
	public void remove(int position) throws Exception {
		Assert.inRange(position, 1, this.length, "position debe estar entre 1 y el largo de la lista");
		this.cursor = null;
		LinkedNode<T> removed = null;
		if (position == 1) {
			removed = this.first;
			this.first = removed.getNext();
		} else {
			LinkedNode<T> previous = this.getNode(position -1);
			removed = previous.getNext();
			previous.setNext( removed.getNext());
		}
		this.length--;
	}

	/**
	 * pre:
	 * @param value: un valor de la lista
	 * @throws Exception 
	 */
	public void remove(T value) throws Exception {
		LinkedNode<T> cursor = this.first;
		int position = 1;
		while (cursor != null) {
			if (cursor.getData().equals(value)) {
				remove(position);
				return;
			}
			cursor = cursor.getNext();
			position++;
		}
		throw new Exception("El valor '" + value + "' no existe");
	}
	
	/**
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * pos: devuelve el dato de la posicion
	 */
	public T get(int position) throws Exception {
		Assert.inRange(position, 1, this.length, "position debe estar entre 1 y el largo de la lista");
		return this.getNode(position).getData();
	}

	/**
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * pos: cambia el elemento de la posicion
	 */
	public void change(T element, int position) throws Exception {
		Assert.inRange(position, 1, this.length, "position debe estar entre 1 y el largo de la lista");
		this.getNode(position).setData(element);
	}

	/*
	 * post: deja el cursor de la Lista preparado para hacer un nuevo
	 *       recorrido sobre sus elementos.
	 */
	public void startCursor() {
		this.cursor = null;
	}

	/*
	 * pre : se ha iniciado un recorrido (invocando el método
	 *       startCursor()) y desde entonces no se han agregado o
	 *       removido elementos de la Lista.
	 * post: mueve el cursor y lo posiciona en el siguiente elemento
	 *       del recorrido.
	 *       El valor de retorno indica si el cursor quedó posicionado
	 *       sobre un elemento o no (en caso de que la Lista esté vacía o
	 *       no existan más elementos por recorrer.)
	 */
	public boolean advanceCursor() {
		if (this.cursor == null) {
			this.cursor = this.first;
		} else {
			this.cursor = this.cursor.getNext();
		}

		/* pudo avanzar si el cursor ahora apunta a un nodo */
		return (this.cursor != null);
	}

	/*
	 * pre: el cursor está posicionado sobre un elemento de la Lista,
	 *       (fue invocado el metodo advanceCursor() y devolvió true)
	 * post: devuelve el elemento en la posición del cursor.
	 *
	 */
	public T obtenerCursor() {
		T element = null;
		if (this.cursor != null) {
			element = this.cursor.getData();
		}
		return element;
	}

	/**
	 * pre: 
	 * @param value: -
	 * post: devuelve verdadero si el valor existe en la lista 
	 */
	public boolean exists(T value) {
		LinkedNode<T> cursor = this.first;
		while (cursor != null) {
			if (cursor.getData().equals(value)) {
				return true;
			}
			cursor = cursor.getNext();
		}
		return false;
	}
	
	/**
	 * pre: 
	 * @param value: -
	 * post: devuelve la cantidad de apariciones del valor en la lista 
	 */
	public int countOcurrences(T value) {
		LinkedNode<T> cursor = this.first;
		int numberOfOcurrences = 0;
		while (cursor != null) {
			if (cursor.getData().equals(value)) {
				numberOfOcurrences++;
			}
			cursor = cursor.getNext();
		}
		return numberOfOcurrences;
	}
	
	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/*
	 * post: devuelve la cantidad de elementos que tiene la Lista.
	 */
	public int getLength() {
		return this.length;
	}

	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: devuelve el nodo en la posición indicada.
	 */
	private LinkedNode<T> getNode(int position) throws Exception {
		Assert.inRange(position, 1, this.length, "position debe estar entre 1 y el largo de la lista");
		LinkedNode<T> actual = this.first;
		for(int i = 1; i < position; i++) {
			actual = actual.getNext();
		}
		return actual;
	}

	//Solucion 2
//	public boolean containsS2(String value) throws Exception {
//		if (value == null) {
//			throw new Exception("El valor no puede ser nulo");
//		}
//		LinkedNode<T> cursor = this.first;
//		while (cursor != null) {
//			Participant participant = (Participant) cursor.getData();
//			if (participant.getName().equals(value)) {
//				return true;
//			}
//			cursor = cursor.getNext();
//		}
//		return false;
//	}

	//Solucion 3
	public boolean containsS3(Object value) throws Exception {
		if (value == null) {
			throw new Exception("El valor no puede ser nulo");
		}
		LinkedNode<T> cursor = this.first;
		while (cursor != null) {
			if (cursor.getData().equals(value)) {
				return true;
			}
			cursor = cursor.getNext();
		}
		return false;
	}
	
	//Solucion 4
	public boolean containsS4(Object value) throws Exception {
		if (value == null) {
			throw new Exception("El valor no puede ser nulo");
		}
		LinkedNode<T> cursor = this.first;
		while (cursor != null) {
			if (cursor.getData().toString().equals(value.toString())) {
				return true;
			}
			cursor = cursor.getNext();
		}
		return false;
	}

	public boolean contains(T value) throws Exception {
		if (value == null) {
			throw new Exception("El valor no puede ser nulo");
		}
		LinkedNode<T> cursor = this.first;
		while (cursor != null) {
			if (cursor.getData().equals(value)) {
				return true;
			}
			cursor = cursor.getNext();
		}
		return false;
	}

	public T getCursor() {
		return this.obtenerCursor();
	}
	
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}






















