package org.thegoats.rolgar2.util.structures.vector;

import org.thegoats.rolgar2.util.Assert;

public class Vector<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private T[] data = null;
	private T firstData;

//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre: 
	 * @param length: entero mayor a 0, determina la cantiadad de elementos del vector
	 * @param firstData: valor inicial para las posiciones del vector
	 * @throws Exception: da error si la longitud es invalida
	 * post: inicializa el vector de longitud de largo y todos los valores inicializados
	 */
	public Vector(int length, T firstData) {
		if (length < 1) {
			throw new RuntimeException("La longitud debe ser mayor o igual a 1");
		}
		this.data = makeVector(length);
		this.firstData = firstData;
		for(int i = 0; i < this.getLength(); i++){
			this.data[i] = firstData;
		}
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	public Vector(Vector<T> vector) {
		this(vector.getLength(), vector.firstData);
		for(int i = 0; i < vector.getLength(); i++){
			this.data[i] = vector.data[i];
		}
	}

	/**
	 * pre:
	 * @param position: valor entre 1 y el largo del vector (no redimensiona)
	 * @param data: -
	 * @throws Exception: da error si la posicion no esta en rango
	 * post: guarda la el dato en la posicion dada 
	 */
	public void add(int position, T data) throws Exception {
		Assert.inRange(position, 1, this.getLength(), "posicion debe estar entre 1 y el largo del vector");
		this.data[position - 1] = data;
	}

	/**
	 * pre: -
	 * @param position: valor entre 1 y el largo del vector
	 * @return devuelve el valor en esa posicion
	 * @throws Exception: da error si la posicion no esta en rango
	 */
	public T get(int position) {
		Assert.inRange( position,1,  this.getLength(), "posicion debe estar entre 1 y el largo del vector");
		return this.data[position - 1];
	}

	/**
	 * pre: -
	 * @param position: valor entre 1 y el largo del vector
	 * @throws Exception: da error si la posicion no esta en rango
	 * post: remueve el valor en la posicion y deja el valor inicial
	 */
	public void remove(int position) throws Exception {
		if ((position < 1) ||
				(position > this.getLength())) {
			throw new Exception("La " + position + " no esta en el rango 1 y " + this.getLength() + " inclusive");
		}
		this.data[position - 1] = this.firstData;
	}

	/**
	 * pre: 
	 * @param data: valor a guardar
	 * @return devuelve la posicion en que se guardo
	 * @throws Exception
	 * post: guarda el dato en la siguiente posicion vacia
	 */
	public int add(T data) {
		//validar dato;
		for(int i = 0; i < this.getLength(); i++) {
			if (this.data[i] == this.firstData) {
				this.data[i] = data;
				return i + 1;
			}
		}		
		T[] temp = makeVector(this.getLength() * 2);
		for(int i = 0; i < this.getLength(); i++) {
			temp[i] = this.data[i];
		}
		int position = this.getLength();
		this.data = temp;
		this.data[position] = data;
		for(int i = position +1; i < this.getLength(); i++) {
			this.data[i] = this.firstData;
		}
		return position + 1;
	}

	/**
	 * pre: 
	 * @param length: -
	 * @return devuelve un vector del tipo y longitud deseado
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	private T[] makeVector(int length) {
		if (length <= 0) {
			throw new RuntimeException("La longitud debe ser mayor o igual a 1");
		}
		return (T[]) new Object[length];
	}
		
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public int getLength() {
		return this.data.length;
	}

	public boolean contains(Character letter) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Devuelve la cantidad de valores distintos del valor inicial
	 * @return
	 */
	public int getDataCount() {
		int dataCount = 0;
		for(T data: this.data) {
			if (data != this.firstData) {
				dataCount++;
			}
		}
		return dataCount;
	}

    /**
     *
     */
    public void reduceSize(int requestedSize) throws Exception {
        Assert.inRange(requestedSize, 1, this.getLength() - this.getDataCount(), "Tamanio Solicitado debe estar entre 1 y el largo del vector");
        Vector<T> temporary = new Vector<T>(requestedSize, null);
        for(int i=1; i < requestedSize; i++){
            int j = 1;
            while(this.get(j) == null){
                j++;
            }
            temporary.add(i, this.get(j));
        }
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	



	













}