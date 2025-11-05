package org.thegoats.rolgar2.util.structures.vector;

import utils.Validaciones;
import utils.Validaciones.*;

public class Vector<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private T[] datos = null;
	private T datoInicial;

//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: 
	 * @param longitud: entero mayor a 0, determina la cantiadad de elementos del vector
	 * @param datoInicial: valor inicial para las posiciones del vector
	 * @throws Exception: da error si la longitud es invalida
	 * post: inicializa el vector de longitud de largo y todos los valores inicializados
	 */
	public Vector(int longitud, T datoInicial) {
		if (longitud < 1) {
			throw new RuntimeException("La longitud debe ser mayor o igual a 1");
		}
		this.datos = crearVector(longitud);
		this.datoInicial = datoInicial;
		for(int i = 0; i < this.getLongitud(); i++){
			this.datos[i] = datoInicial;
		}
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	public Vector(Vector<T> vector) {
		this(vector.getLongitud(), vector.datoInicial);
		for(int i = 0; i < vector.getLongitud(); i++){
			this.datos[i] = vector.datos[i];
		}
	}

	/**
	 * pre:
	 * @param posicion: valor entre 1 y el largo del vector (no redimensiona)
	 * @param dato: -
	 * @throws Exception: da error si la posicion no esta en rango
	 * post: guarda la el dato en la posicion dada 
	 */
	public void agregar(int posicion, T dato) throws Exception {
		Validaciones.validarRangoNumerico(posicion, 1, this.getLongitud(), "posicion");
		this.datos[posicion - 1] = dato;
	}

	/**
	 * pre: -
	 * @param posicion: valor entre 1 y el largo del vector
	 * @return devuelve el valor en esa posicion
	 * @throws Exception: da error si la posicion no esta en rango
	 */
	public T obtener(int posicion) {
		Validaciones.validarRangoNumerico( posicion,1,  this.getLongitud(), "posicion");
		return this.datos[posicion - 1];
	}

	/**
	 * pre: -
	 * @param posicion: valor entre 1 y el largo del vector
	 * @throws Exception: da error si la posicion no esta en rango
	 * post: remueve el valor en la posicion y deja el valor inicial
	 */
	public void remover(int posicion) throws Exception {
		if ((posicion < 1) ||
				(posicion > this.getLongitud())) {
			throw new Exception("La " + posicion + " no esta en el rango 1 y " + this.getLongitud() + " inclusive");
		}
		this.datos[posicion - 1] = this.datoInicial;
	}

	/**
	 * pre: 
	 * @param dato: valor a guardar
	 * @return devuelve la posicion en que se guardo
	 * @throws Exception
	 * post: guarda el dato en la siguiente posicion vacia
	 */
	public int agregar(T dato) {
		//validar dato;
		for(int i = 0; i < this.getLongitud(); i++) {
			if (this.datos[i] == this.datoInicial) {
				this.datos[i] = dato;
				return i + 1;
			}
		}		
		T[] temp = crearVector(this.getLongitud() * 2);
		for(int i = 0; i < this.getLongitud(); i++) {
			temp[i] = this.datos[i];
		}
		int posicion = this.getLongitud();		
		this.datos = temp;
		this.datos[posicion] = dato;		
		for(int i = posicion +1; i < this.getLongitud(); i++) {
			this.datos[i] = this.datoInicial;	
		}
		return posicion + 1;
	}

	/**
	 * pre: 
	 * @param longitud: -
	 * @return devuelve un vector del tipo y longitud deseado
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	private T[] crearVector(int longitud) {
		if (longitud <= 0) {
			throw new RuntimeException("La longitud debe ser mayor o igual a 1");
		}
		return (T[]) new Object[longitud];
	}
		
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public int getLongitud() {
		return this.datos.length;
	}

	public boolean contiene(Character letra) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Devuelve la cantidad de valores distintos del valor inicial
	 * @return
	 */
	public int getCantidadDeDatos() {
		int cantidadDeDatos = 0;
		for(T dato: this.datos) {
			if (dato != this.datoInicial) {
				cantidadDeDatos++;
			}
		}
		return cantidadDeDatos;
	}

    /**
     *
     */
    public void reducirTamanio(int tamanioSolicitado) throws Exception {
        Validaciones.validarRangoNumerico(tamanioSolicitado, 1, this.getLongitud() - this.getCantidadDeDatos(), "Tamanio Solicitado");
        Vector<T> nuevo = new Vector<T>(tamanioSolicitado, null);
        for(int i=1; i < tamanioSolicitado; i++){
            int j = 1;
            while(this.obtener(j) == null){
                j++;
            }
            nuevo.agregar(i, this.obtener(j));
        }
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	



	













}