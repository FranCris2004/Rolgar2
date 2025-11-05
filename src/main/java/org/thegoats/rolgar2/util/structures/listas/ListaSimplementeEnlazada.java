package org.thegoats.rolgar2.util.structures.listas;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import estructuras.nodos.NodoSimplementeEnlazado;

public class ListaSimplementeEnlazada<T> implements List<T> {
//INTERFACES ----------------------------------------------------------------------------------------------
//ENUMERADOS ----------------------------------------------------------------------------------------------
//CONSTANTES ----------------------------------------------------------------------------------------------
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
    private NodoSimplementeEnlazado<T> primero = null;
    private int tamanio = 0;
    private NodoSimplementeEnlazado<T> cursor = null;
    
//ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    
	/**
	 * pre:
	 * pos: crea una lista vacia
	 */
    public ListaSimplementeEnlazada() {}
    
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS HEREDADOS (CLASE)--------------------------------------------------------------------------------
//METODOS HEREDADOS (INTERFACE)----------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    
	/**
	 * post: deja el cursor de la Lista preparado para hacer un nuevo
	 *       recorrido sobre sus elementos.
	 */
	public void iniciarCursor() {
		this.cursor = null;
	}
	
	/**
	 * pre : se ha iniciado un recorrido (invocando el método
	 *       iniciarCursor()) y desde entonces no se han agregado o
	 *       removido elementos de la Lista.
	 * post: mueve el cursor y lo posiciona en el siguiente elemento
	 *       del recorrido.
	 *       El valor de retorno indica si el cursor quedó posicionado
	 *       sobre un elemento o no (en caso de que la Lista esté vacía o
	 *       no existan más elementos por recorrer.)
	 */
	public boolean avanzarCursor() {
		if (this.cursor == null) {
			this.cursor = this.primero;
		} else {
			this.cursor = this.cursor.getSiguiente();
		}

		/* pudo avanzar si el cursor ahora apunta a un nodo */
		return (this.cursor != null);
	}
	
	/**
	 * pre : el cursor está posicionado sobre un elemento de la Lista,
	 *       (fue invocado el método avanzarCursor() y devolvió true)
	 * post: devuelve el elemento en la posición del cursor.
	 *
	 */
	public T obtenerCursor() {
		T elemento = null;
		if (this.cursor != null) {
			elemento = this.cursor.getDato();
		}
		return elemento;
	}
	
	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
    @Override
    public boolean add(T elemento) {
        addLast(elemento);
        return true;
    }
    
	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
    public void addLast(T elemento) {
        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
        if (primero == null) {
            primero = nuevo;
        } else {
            NodoSimplementeEnlazado<T> actual = primero;
            while (actual.tieneSiguiente()) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }
    
	/**
	 * pre: -
	 * pos: agrega el elemento en una posicion de la Lista, en la posición:
	 *       index, en el rango de 1 a n
	 */
    
    @Override
    public void add(int index, T elemento) {
        if (index < 0 || index > tamanio) {
        	throw new IndexOutOfBoundsException();
        }

        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
        if (index == 0) {
            nuevo.setSiguiente(primero);
            primero = nuevo;
        } else {
            NodoSimplementeEnlazado<T> actual = primero;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }
    
	/**
	 * pre:
	 * pos: indica si la Lista tiene algún elemento.
	 */
    @Override
    public boolean isEmpty() {
        return tamanio == 0;
    }
    
    /**
     * Devuelve el elemento de una posicion de la lista
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        NodoSimplementeEnlazado<T> actual = primero;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    /**
     * Demueve el elemento de una posicion de la lista
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        NodoSimplementeEnlazado<T> eliminado;
        if (index == 0) {
            eliminado = primero;
            primero = primero.getSiguiente();
        } else {
            NodoSimplementeEnlazado<T> actual = primero;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getSiguiente();
            }
            eliminado = actual.getSiguiente();
            actual.setSiguiente( eliminado.getSiguiente());
        }
        tamanio--;
        return eliminado.getDato();
    }

    /**
     * Devuelve el tamaña
     */
    @Override
    public int size() {
        return tamanio;
    }
    
    /**
     * Crea un iterador para recorrer la lista
     */
    @Override
    public Iterator<T> iterator() {
    	return new Iterator<T>() {
    		// 'next' apunta al siguiente nodo que será devuelto por next()
    		private NodoSimplementeEnlazado<T> next = primero;
    		// último nodo retornado por next()/previous()
    		private NodoSimplementeEnlazado<T> lastReturned = null;
    		// nodo previo al lastReturned (necesario para poder eliminar en O(1) relativo a la operación)
    		private NodoSimplementeEnlazado<T> prevOfLastReturned = null;
    		// nodo previo al 'next' (es el nodo previo antes de hacer next())
    		private NodoSimplementeEnlazado<T> beforeNext = null;


    		@Override
    		public boolean hasNext() {
    		return next != null;
    		}


    		@Override
    		public T next() {
    		if (next == null) throw new java.util.NoSuchElementException();
    		// prevOfLastReturned será el nodo "beforeNext" (node before the node we're about to return)
    		prevOfLastReturned = beforeNext;
    		lastReturned = next;
    		// avanzo
    		next = next.getSiguiente();
    		// ahora 'beforeNext' pasa a ser el nodo que acabo de devolver
    		beforeNext = lastReturned;
    		return lastReturned.getDato();
    		}


    		@Override
    		public void remove() {
    		if (lastReturned == null) throw new IllegalStateException();


    		// Si prevOfLastReturned es null, entonces lastReturned era el primero
    		if (prevOfLastReturned == null) {
    		// eliminar la cabeza
    		primero = next;
    		} else {
    		// conectar prevOfLastReturned con 'next' (nodo que quedó después del eliminado)
    		prevOfLastReturned.setSiguiente(next);
    		}


    		// ajustar tamaño
    		tamanio--;


    		// después de eliminar, no hay último retornado válido hasta la próxima next()
    		lastReturned = null;
    		// 'beforeNext' debe quedar apuntando al nodo previo a 'next' (que ahora es prevOfLastReturned)
    		beforeNext = prevOfLastReturned;
    		}
    		};
    }
    
    /**
     * Devuelve verdadero si contiene el elemento
     */
    @Override
    public boolean contains(Object o) {
        for (T elemento : this) {
            if (Objects.equals(elemento, o)) return true;
        }
        return false;
    }

    /**
     * Elimina un elemento de la lista y sino lo encuentra devuelve falso
     */
    @Override
    public boolean remove(Object o) {
        if (primero == null) return false;

        if (Objects.equals(primero.getDato(), o)) {
            primero = primero.getSiguiente();
            tamanio--;
            return true;
        }

        NodoSimplementeEnlazado<T> actual = primero;
        while (actual.tieneSiguiente() && 
        	   !Objects.equals(actual.getSiguiente().getDato(), o)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            tamanio--;
            return true;
        }

        return false;
    }

    /**
     * Elimina todos los elementos de la lista
     */
    @Override
    public void clear() {
        primero = null;
        tamanio = 0;
    }

    /**
     * cambia el elemento de una posicion
     */
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        NodoSimplementeEnlazado<T> actual = primero;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        T viejo = actual.getDato();
        actual.setDato(element);
        return viejo;
    }

    /**
     * Devuelve el indice de un item en la lista
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (T elemento : this) {
            if (Objects.equals(elemento, o)) return index;
            index++;
        }
        return -1;
    }

    /**
     * Devuelve el ultimo indice de un elmento en la lista
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = 0, ultimo = -1;
        for (T elemento : this) {
            if (Objects.equals(elemento, o)) ultimo = index;
            index++;
        }
        return ultimo;
    }


    @Override
    public Object[] toArray() {
        Object[] array = new Object[tamanio];
        int i = 0;
        for (T elem : this) {
            array[i++] = elem;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < tamanio) {
            a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), tamanio);
        }

        int i = 0;
        for (T elem : this) {
            a[i++] = (E) elem;
        }

        if (a.length > tamanio) {
            a[tamanio] = null;
        }

        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean cambiado = false;
        for (T elem : c) {
            add(elem);
            cambiado = true;
        }
        return cambiado;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();

        boolean modificado = false;
        for (T elem : c) {
            add(index++, elem);
            modificado = true;
        }
        return modificado;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modificado = false;
        for (Object elem : c) {
            while (remove(elem)) {
                modificado = true;
            }
        }
        return modificado;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modificado = false;
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modificado = true;
            }
        }
        return modificado;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListaSimpleIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();
        return new ListaSimpleIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > tamanio || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        ListaSimplementeEnlazada<T> sublista = new ListaSimplementeEnlazada<>();
        NodoSimplementeEnlazado<T> actual = primero;
        for (int i = 0; i < toIndex; i++) {
            if (i >= fromIndex) sublista.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return sublista;
    }
    
//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------	
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS COMPLEJOS----------------------------------------------------------------------------------------	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
   
	private class ListaSimpleIterator implements ListIterator<T> {
	    private NodoSimplementeEnlazado<T> actual;
	    private NodoSimplementeEnlazado<T> ultimoRetornado;
	    private int cursorIndex;

	    public ListaSimpleIterator(int index) {
	        this.actual = primero;
	        this.ultimoRetornado = null;
	        this.cursorIndex = 0;

	        // Avanzo hasta la posición inicial
	        for (int i = 0; i < index; i++) {
	            this.actual = this.actual.getSiguiente();
	            this.cursorIndex++;
	        }
	    }

	    @Override
	    public boolean hasNext() {
	        return actual != null;
	    }

	    @Override
	    public T next() {
	        if (actual == null) throw new java.util.NoSuchElementException();
	        ultimoRetornado = actual;
	        T dato = actual.getDato();
	        actual = actual.getSiguiente();
	        cursorIndex++;
	        return dato;
	    }

	    @Override
	    public boolean hasPrevious() {
	        return cursorIndex > 0;
	    }

	    @Override
	    public T previous() {
	        if (!hasPrevious()) throw new java.util.NoSuchElementException();

	        // Para ir hacia atrás debo recorrer desde el inicio
	        NodoSimplementeEnlazado<T> nodo = primero;
	        for (int i = 0; i < cursorIndex - 1; i++) {
	            nodo = nodo.getSiguiente();
	        }

	        actual = nodo;
	        ultimoRetornado = nodo;
	        cursorIndex--;
	        return nodo.getDato();
	    }

	    @Override
	    public int nextIndex() {
	        return cursorIndex;
	    }

	    @Override
	    public int previousIndex() {
	        return cursorIndex - 1;
	    }

	    @Override
	    public void remove() {
	        if (ultimoRetornado == null) throw new IllegalStateException();

	        ListaSimplementeEnlazada.this.remove(cursorIndex - 1);
	        cursorIndex--;
	        ultimoRetornado = null;
	    }

	    @Override
	    public void set(T t) {
	        if (ultimoRetornado == null) throw new IllegalStateException();
	        ultimoRetornado.setDato(t);
	    }

	    @Override
	    public void add(T t) {
	        ListaSimplementeEnlazada.this.add(cursorIndex, t);
	        cursorIndex++;
	        ultimoRetornado = null;
	    }
	}
	
	/**
	 * 
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	public void addSorted(T item) {
	    if (item == null) {
	    	throw new NullPointerException("No se puede insertar null en la lista");
	    }
	    if (!(item instanceof Comparable)) {
	        throw new IllegalArgumentException("El tipo debe implementar Comparable");
	    }
	    NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(item);

	    // Caso 1: lista vacía o el item es menor o igual al primero
	    if (primero == null || 
	    	((Comparable<T>) item).compareTo(primero.getDato()) <= 0) {
	        nuevo.setSiguiente(primero);
	        primero = nuevo;
	    } else {
	        // Recorro hasta encontrar el lugar donde insertar
	        NodoSimplementeEnlazado<T> actual = primero;
	        while (actual.tieneSiguiente() &&
	               ((Comparable<T>) item).compareTo(actual.getSiguiente().getDato()) > 0) {
	            actual = actual.getSiguiente();
	        }
	        nuevo.setSiguiente(actual.getSiguiente());
	        actual.setSiguiente(nuevo);
	    }
	    tamanio++;
	}
		
}