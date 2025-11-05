package org.thegoats.rolgar2.util.structures.listas;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import estructuras.nodos.NodoDoblementeEnlazado;

public class ListaDoblementeEnlazada<T> implements List<T> {
    private NodoDoblementeEnlazado<T> primero = null;
    private NodoDoblementeEnlazado<T> ultimo = null;
    private int tamanio = 0;

    public ListaDoblementeEnlazada() {}

    @Override
    public int size() {
        return tamanio;
    }

    @Override
    public boolean isEmpty() {
        return tamanio == 0;
    }

    @Override
    public boolean add(T elemento) {
        NodoDoblementeEnlazado<T> nuevo = new NodoDoblementeEnlazado<>(elemento);
        if (ultimo == null) {
            primero = ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            ultimo = nuevo;
        }
        tamanio++;
        return true;
    }

    @Override
    public void add(int index, T elemento) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();

        NodoDoblementeEnlazado<T> nuevo = new NodoDoblementeEnlazado<>(elemento);

        if (index == tamanio) { 
            add(elemento);
            return;
        }
        if (index == 0) {
            nuevo.setSiguiente(primero);
            if (primero != null) primero.setAnterior(nuevo);
            primero = nuevo;
            if (ultimo == null) ultimo = nuevo;
        } else {
            NodoDoblementeEnlazado<T> actual = getNode(index);
            NodoDoblementeEnlazado<T> anterior = actual.getAnterior();
            nuevo.setAnterior(anterior);
            nuevo.setSiguiente(actual);
            anterior.setSiguiente(nuevo);
            actual.setAnterior(nuevo);
        }
        tamanio++;
    }

    @Override
    public T get(int index) {
        return getNode(index).getDato();
    }

    @Override
    public T set(int index, T elemento) {
        NodoDoblementeEnlazado<T> nodo = getNode(index);
        T viejo = nodo.getDato();
        nodo.setDato(elemento);
        return viejo;
    }

    @Override
    public T remove(int index) {
        NodoDoblementeEnlazado<T> nodo = getNode(index);
        T dato = nodo.getDato();

        NodoDoblementeEnlazado<T> ant = nodo.getAnterior();
        NodoDoblementeEnlazado<T> sig = nodo.getSiguiente();

        if (ant != null) {
            ant.setSiguiente(sig);
        } else {
            primero = sig;
        }

        if (sig != null) {
            sig.setAnterior(ant);
        } else {
            ultimo = ant;
        }

        tamanio--;
        return dato;
    }

    @Override
    public boolean remove(Object o) {
        NodoDoblementeEnlazado<T> actual = primero;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), o)) {
                NodoDoblementeEnlazado<T> ant = actual.getAnterior();
                NodoDoblementeEnlazado<T> sig = actual.getSiguiente();

                if (ant != null) ant.setSiguiente(sig);
                else primero = sig;

                if (sig != null) sig.setAnterior(ant);
                else ultimo = ant;

                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    private NodoDoblementeEnlazado<T> getNode(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        NodoDoblementeEnlazado<T> actual;
        if (index < tamanio / 2) {
            actual = primero;
            for (int i = 0; i < index; i++) actual = actual.getSiguiente();
        } else {
            actual = ultimo;
            for (int i = tamanio - 1; i > index; i--) actual = actual.getAnterior();
        }
        return actual;
    }

    @Override
    public void clear() {
        primero = ultimo = null;
        tamanio = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            NodoDoblementeEnlazado<T> actual = primero;

            public boolean hasNext() {
                return actual != null;
            }

            public T next() {
                if (actual == null) throw new NoSuchElementException();
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    // --- Métodos que no pediste explícitamente pero exige List<T> ---
    @Override public boolean contains(Object o) { return indexOf(o) >= 0; }
    @Override public int indexOf(Object o) {
        int i = 0;
        for (T elem : this) {
            if (Objects.equals(elem, o)) return i;
            i++;
        }
        return -1;
    }
    @Override public int lastIndexOf(Object o) {
        int i = tamanio - 1;
        NodoDoblementeEnlazado<T> actual = ultimo;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), o)) return i;
            actual = actual.getAnterior();
            i--;
        }
        return -1;
    }
    @Override public Object[] toArray() { Object[] arr = new Object[tamanio]; int i=0; for(T e: this) arr[i++] = e; return arr; }
    @Override @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        if (a.length < tamanio) a = (E[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), tamanio);
        int i=0; for(T e: this) a[i++] = (E)e;
        if (a.length > tamanio) a[tamanio] = null;
        return a;
    }
    @Override public boolean addAll(Collection<? extends T> c) { boolean mod=false; for(T e: c) {add(e); mod=true;} return mod; }
    @Override public boolean addAll(int index, Collection<? extends T> c) { int i=index; boolean mod=false; for(T e:c){ add(i++,e); mod=true;} return mod; }
    @Override public boolean removeAll(Collection<?> c) { boolean mod=false; for(Object o:c) while(remove(o)) mod=true; return mod; }
    @Override public boolean retainAll(Collection<?> c) { boolean mod=false; Iterator<T> it=iterator(); while(it.hasNext()){ if(!c.contains(it.next())){it.remove(); mod=true;}} return mod; }
    @Override public List<T> subList(int fromIndex, int toIndex) { 
        if(fromIndex<0||toIndex>tamanio||fromIndex>toIndex) throw new IndexOutOfBoundsException();
        ListaDoblementeEnlazada<T> sub = new ListaDoblementeEnlazada<>();
        NodoDoblementeEnlazado<T> actual = getNode(fromIndex);
        for(int i=fromIndex;i<toIndex;i++){ sub.add(actual.getDato()); actual=actual.getSiguiente(); }
        return sub;
    }

	@Override
	public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) return false;
        }
        return true;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return new ListIterator<T>() {
            NodoDoblementeEnlazado<T> actual = primero;
            NodoDoblementeEnlazado<T> ultimoRetornado = null;
            int index = 0;

            public boolean hasNext() { return actual != null; }

            public T next() {
                if (actual == null) throw new NoSuchElementException();
                ultimoRetornado = actual;
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                index++;
                return dato;
            }

            public boolean hasPrevious() { return (actual != null && actual.getAnterior() != null) || (actual == null && ultimo != null); }

            public T previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                if (actual == null) { // estamos al final
                    actual = ultimo;
                    index = tamanio - 1;
                } else {
                    actual = actual.getAnterior();
                    index--;
                }
                ultimoRetornado = actual;
                return actual.getDato();
            }

            public int nextIndex() { return index; }
            public int previousIndex() { return index - 1; }

            public void remove() {
                if (ultimoRetornado == null) throw new IllegalStateException();
                ListaDoblementeEnlazada.this.remove(index - 1);
                ultimoRetornado = null;
            }

            public void set(T e) {
                if (ultimoRetornado == null) throw new IllegalStateException();
                ultimoRetornado.setDato(e);
            }

            public void add(T e) {
                ListaDoblementeEnlazada.this.add(index, e);
                index++;
                ultimoRetornado = null;
            }
        };
	}
	
	public void addSorted(T elemento) {
	    if (!(elemento instanceof Comparable)) {
	        throw new IllegalArgumentException("El elemento no implementa Comparable");
	    }

	    @SuppressWarnings("unchecked")
	    Comparable<? super T> cmp = (Comparable<? super T>) elemento;

	    NodoDoblementeEnlazado<T> nuevo = new NodoDoblementeEnlazado<>(elemento);

	    if (primero == null) { // lista vacía
	        primero = ultimo = nuevo;
	    } else if (cmp.compareTo(primero.getDato()) <= 0) { // insertar al inicio
	        nuevo.setSiguiente(primero);
	        primero.setAnterior(nuevo);
	        primero = nuevo;
	    } else if (cmp.compareTo(ultimo.getDato()) >= 0) { // insertar al final
	        ultimo.setSiguiente(nuevo);
	        nuevo.setAnterior(ultimo);
	        ultimo = nuevo;
	    } else { // insertar en el medio
	        NodoDoblementeEnlazado<T> actual = primero;
	        while (actual != null && cmp.compareTo(actual.getDato()) > 0) {
	            actual = actual.getSiguiente();
	        }
	        NodoDoblementeEnlazado<T> anterior = actual.getAnterior();
	        nuevo.setAnterior(anterior);
	        nuevo.setSiguiente(actual);
	        anterior.setSiguiente(nuevo);
	        actual.setAnterior(nuevo);
	    }
	    tamanio++;
	}
}

