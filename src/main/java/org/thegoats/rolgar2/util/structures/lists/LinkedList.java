package org.thegoats.rolgar2.util.structures.lists;
import java.lang.reflect.Array;
import java.util.*;
import java.util.ListIterator;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;

public class LinkedList<T> implements List<T> {
//INTERFACES ----------------------------------------------------------------------------------------------
//ENUMERADOS ----------------------------------------------------------------------------------------------
//CONSTANTES ----------------------------------------------------------------------------------------------
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
    private LinkedNode<T> first = null;
    private int size = 0;
    private LinkedNode<T> cursor = null;
    
//ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    
	/**
	 * pre:
	 * pos: crea una lista vacia
	 */
    public LinkedList() {}
    
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
	public void startCursor() {
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
	public boolean advanceCursor() {
		if (this.cursor == null) {
			this.cursor = this.first;
		} else {
			this.cursor = this.cursor.getNext();
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
	public T getCursor() {
		T element = null;
		if (this.cursor != null) {
			element = this.cursor.getData();
		}
		return element;
	}
	
	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
    @Override
    public boolean add(T element) {
        addLast(element);
        return true;
    }
    
	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
    public void addLast(T element) {
        LinkedNode<T> temporary = new LinkedNode<>(element);
        if (first == null) {
            first = temporary;
        } else {
            LinkedNode<T> actual = first;
            while (actual.hasNext()) {
                actual = actual.getNext();
            }
            actual.setNext(temporary);
        }
        size++;
    }
    
	/**
	 * pre: -
	 * pos: agrega el elemento en una posicion de la Lista, en la posición:
	 *       index, en el rango de 1 a n
	 */
    
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
        	throw new IndexOutOfBoundsException();
        }

        LinkedNode<T> temporary = new LinkedNode<>(element);
        if (index == 0) {
            temporary.setNext(first);
            first = temporary;
        } else {
            LinkedNode<T> actual = first;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getNext();
            }
            temporary.setNext(actual.getNext());
            actual.setNext(temporary);
        }
        size++;
    }
    
	/**
	 * pre:
	 * pos: indica si la Lista tiene algún elemento.
	 */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Devuelve el elemento de una posicion de la lista
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        LinkedNode<T> actual = first;
        for (int i = 0; i < index; i++) {
            actual = actual.getNext();
        }
        return actual.getData();
    }

    /**
     * Demueve el elemento de una posicion de la lista
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        LinkedNode<T> removed;
        if (index == 0) {
            removed = first;
            first = first.getNext();
        } else {
            LinkedNode<T> actual = first;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getNext();
            }
            removed = actual.getNext();
            actual.setNext( removed.getNext());
        }
        size--;
        return removed.getData();
    }

    /**
     * Devuelve el tamaña
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Crea un iterador para recorrer la lista
     */
    @Override
    public Iterator<T> iterator() {
    	return new Iterator<T>() {
    		// 'next' apunta al siguiente nodo que será devuelto por next()
    		private LinkedNode<T> next = first;
    		// último nodo retornado por next()/previous()
    		private LinkedNode<T> lastReturned = null;
    		// nodo previo al lastReturned (necesario para poder eliminar en O(1) relativo a la operación)
    		private LinkedNode<T> prevOfLastReturned = null;
    		// nodo previo al 'next' (es el nodo previo antes de hacer next())
    		private LinkedNode<T> beforeNext = null;


    		@Override
    		public boolean hasNext() {
    		return next != null;
    		}


    		@Override
    		public T next() {
    		if (next == null) throw new NoSuchElementException();
    		// prevOfLastReturned será el nodo "beforeNext" (node before the node we're about to return)
    		prevOfLastReturned = beforeNext;
    		lastReturned = next;
    		// avanzo
    		next = next.getNext();
    		// ahora 'beforeNext' pasa a ser el nodo que acabo de devolver
    		beforeNext = lastReturned;
    		return lastReturned.getData();
    		}


    		@Override
    		public void remove() {
    		if (lastReturned == null) throw new IllegalStateException();

    		// Si prevOfLastReturned es null, entonces lastReturned era el primero
    		if (prevOfLastReturned == null) {
    		// eliminar la cabeza
    		first = next;
    		} else {
    		// conectar prevOfLastReturned con 'next' (nodo que quedó después del eliminado)
    		prevOfLastReturned.setNext(next);
    		}

    		// ajustar tamaño
    		size--;

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
        for (T element : this) {
            if (Objects.equals(element, o)) return true;
        }
        return false;
    }

    /**
     * Elimina un elemento de la lista y sino lo encuentra devuelve falso
     */
    @Override
    public boolean remove(Object o) {
        if (first == null) return false;

        if (Objects.equals(first.getData(), o)) {
            first = first.getNext();
            size--;
            return true;
        }

        LinkedNode<T> actual = first;
        while (actual.hasNext() &&
        	   !Objects.equals(actual.getNext().getData(), o)) {
            actual = actual.getNext();
        }

        if (actual.getNext() != null) {
            actual.setNext(actual.getNext().getNext());
            size--;
            return true;
        }

        return false;
    }

    /**
     * Elimina todos los elementos de la lista
     */
    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * cambia el elemento de una posicion
     */
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        LinkedNode<T> actual = first;
        for (int i = 0; i < index; i++) {
            actual = actual.getNext();
        }
        T old = actual.getData();
        actual.setData(element);
        return old;
    }

    /**
     * Devuelve el indice de un item en la lista
     */
    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (T element : this) {
            if (Objects.equals(element, o)) return index;
            index++;
        }
        return -1;
    }

    /**
     * Devuelve el ultimo indice de un elmento en la lista
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = 0, last = -1;
        for (T element : this) {
            if (Objects.equals(element, o)) last = index;
            index++;
        }
        return last;
    }


    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (T elem : this) {
            array[i++] = elem;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        for (T elem : this) {
            a[i++] = (E) elem;
        }

        if (a.length > size) {
            a[size] = null;
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
        boolean changed = false;
        for (T elem : c) {
            add(elem);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        boolean changed = false;
        for (T elem : c) {
            add(index++, elem);
            changed = true;
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object elem : c) {
            while (remove(elem)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new SimpleListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        return new SimpleListIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        LinkedList<T> sublist = new LinkedList<>();
        LinkedNode<T> actual = first;
        for (int i = 0; i < toIndex; i++) {
            if (i >= fromIndex) sublist.add(actual.getData());
            actual = actual.getNext();
        }
        return sublist;
    }
    
//METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------	
//GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
//GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
//GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS COMPLEJOS----------------------------------------------------------------------------------------	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
   
	private class SimpleListIterator implements ListIterator<T> {
	    private LinkedNode<T> actual;
	    private LinkedNode<T> lastReturned;
	    private int cursorIndex;

	    public SimpleListIterator(int index) {
	        this.actual = first;
	        this.lastReturned = null;
	        this.cursorIndex = 0;

	        // Avanzo hasta la posición inicial
	        for (int i = 0; i < index; i++) {
	            this.actual = this.actual.getNext();
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
	        lastReturned = actual;
	        T data = actual.getData();
	        actual = actual.getNext();
	        cursorIndex++;
	        return data;
	    }

	    @Override
	    public boolean hasPrevious() {
	        return cursorIndex > 0;
	    }

	    @Override
	    public T previous() {
	        if (!hasPrevious()) throw new java.util.NoSuchElementException();

	        // Para ir hacia atrás debo recorrer desde el inicio
            LinkedNode<T> node = first;
	        for (int i = 0; i < cursorIndex - 1; i++) {
	            node = node.getNext();
	        }

	        actual = node;
	        lastReturned = node;
	        cursorIndex--;
	        return node.getData();
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
	        if (lastReturned == null) throw new IllegalStateException();

	        LinkedList.this.remove(cursorIndex - 1);
	        cursorIndex--;
	        lastReturned = null;
	    }

	    @Override
	    public void set(T t) {
	        if (lastReturned == null) throw new IllegalStateException();
	        lastReturned.setData(t);
	    }

	    @Override
	    public void add(T t) {
	        LinkedList.this.add(cursorIndex, t);
	        cursorIndex++;
	        lastReturned = null;
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
        LinkedNode<T> temporary = new LinkedNode<>(item);

	    // Caso 1: lista vacía o el item es menor o igual al primero
	    if (first == null ||
	    	((Comparable<T>) item).compareTo(first.getData()) <= 0) {
	        temporary.setNext(first);
	        first = temporary;
	    } else {
	        // Recorro hasta encontrar el lugar donde insertar
            LinkedNode<T> actual = first;
	        while (actual.hasNext() &&
	               ((Comparable<T>) item).compareTo(actual.getNext().getData()) > 0) {
	            actual = actual.getNext();
	        }
	        temporary.setNext(actual.getNext());
	        actual.setNext(temporary);
	    }
	    size++;
	}
		
}