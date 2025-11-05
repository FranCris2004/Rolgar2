package org.thegoats.rolgar2.util.structures.cola;

import java.util.List;

import estructuras.nodos.NodoSimplementeEnlazado;

public class ColaBasica<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private NodoSimplementeEnlazado<T> frente = null;

	private NodoSimplementeEnlazado<T> ultimo = null;

	private int tamanio = 0;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la cola vacia para su uso
	 */
	public ColaBasica() {
		this.frente = null;
		this.ultimo = null;
		this.tamanio = 0;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	/*
	 * post: indica si la cola tiene algún elemento.
	 */
	public boolean estaVacia() {
		return (this.tamanio == 0);
	}

	/*
	 * pre: el elemento no es vacio
	 * post: agrega el elemento a la cola
	 */
	public void acolar(T elemento) {
		NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<T>(elemento);
		if (!this.estaVacia()) {
			this.ultimo.setSiguiente(nuevo);
			this.ultimo = nuevo;
		} else {
			this.frente = nuevo;
			this.ultimo = nuevo;
		}
		this.tamanio++;
	}

	/*
	 * pre: el elemento no es vacio
	 * post: agrega el elemento a la cola
	 */
	void acolar(List<T> lista) {
		//validar
		for(T dato: lista) {
			this.acolar( dato );
		}
	}

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola quitandolo.
	 */
	public T desacolar() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.frente.getDato();
			this.frente = this.frente.getSiguiente();
			this.tamanio--;
			if (estaVacia()) {
				this.ultimo = null;
			}
		}
		return elemento;
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int contarElementos() {

		return this.tamanio;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola. Solo lectura
	 */
	public T obtener() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.frente.getDato();
		}
		return elemento;
	}

	public void acolarAll(List<T> lista) {
		for(T item: lista) {
			this.acolar(item);
		}		
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}