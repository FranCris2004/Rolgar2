package org.thegoats.rolgar2.util.structures.pilas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import estructuras.nodos.NodoSimplementeEnlazado;

public class Pila<T> implements Deque<T> {
	private NodoSimplementeEnlazado<T> tope;
	private int tamanio;

	public Pila() {
		this.tope = null;
		this.tamanio = 0;
	}

	// ---- Operaciones principales de Pila ----
	@Override
	public void push(T elemento) {
		addFirst(elemento);
	}

	@Override
	public T pop() {
		return removeFirst();
	}

	@Override
	public T peek() {
		return peekFirst();
	}

	// ---- Implementación de Deque ----
	@Override
	public void addFirst(T elemento) {
		NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
		nuevo.setSiguiente(tope);
		tope = nuevo;
		tamanio++;
	}

	@Override
	public void addLast(T elemento) {
		// En una pila no se usa normalmente, pero lo implementamos por contrato
		NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
		if (isEmpty()) {
			tope = nuevo;
		} else {
			NodoSimplementeEnlazado<T> actual = tope;
			while (actual.getSiguiente() != null) {
				actual = actual.getSiguiente();
			}
			actual.setSiguiente(nuevo);
		}
		tamanio++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		T dato = tope.getDato();
		tope = tope.getSiguiente();
		tamanio--;
		return dato;
	}

	@Override
	public T removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		if (tope.getSiguiente() == null) {
			T dato = tope.getDato();
			tope = null;
			tamanio--;
			return dato;
		}
		NodoSimplementeEnlazado<T> actual = tope;
		while (actual.getSiguiente().getSiguiente() != null) {
			actual = actual.getSiguiente();
		}
		T dato = actual.getSiguiente().getDato();
		actual.setSiguiente(null);
		tamanio--;
		return dato;
	}

	@Override
	public T peekFirst() {
		return (tope == null) ? null : tope.getDato();
	}

	@Override
	public T peekLast() {
		if (isEmpty())
			return null;
		NodoSimplementeEnlazado<T> actual = tope;
		while (actual.getSiguiente() != null) {
			actual = actual.getSiguiente();
		}
		return actual.getDato();
	}

	// ---- Métodos utilitarios ----
	@Override
	public int size() {
		return tamanio;
	}

	@Override
	public boolean isEmpty() {
		return tamanio == 0;
	}

	@Override
	public void clear() {
		tope = null;
		tamanio = 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			NodoSimplementeEnlazado<T> actual = tope;

			@Override
			public boolean hasNext() {
				return actual != null;
			}

			@Override
			public T next() {
				if (actual == null)
					throw new NoSuchElementException();
				T dato = actual.getDato();
				actual = actual.getSiguiente();
				return dato;
			}
		};
	}

	@Override
	public Iterator<T> descendingIterator() {
		// para cumplir contrato, aunque sea O(n)
		List<T> lista = new ArrayList<>();
		for (T e : this)
			lista.add(e);
		Collections.reverse(lista);
		return lista.iterator();
	}

	// ---- Métodos de Deque que no son esenciales para pila ----
	@Override
	public boolean offerFirst(T e) {
		addFirst(e);
		return true;
	}

	@Override
	public boolean offerLast(T e) {
		addLast(e);
		return true;
	}

	@Override
	public T pollFirst() {
		return isEmpty() ? null : removeFirst();
	}

	@Override
	public T pollLast() {
		return isEmpty() ? null : removeLast();
	}

	@Override
	public T getFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		return tope.getDato();
	}

	@Override
	public T getLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		return peekLast();
	}

	@Override
	public boolean offer(T e) {
		return offerLast(e);
	}

	@Override
	public T remove() {
		return removeFirst();
	}

	@Override
	public T poll() {
		return pollFirst();
	}

	@Override
	public T element() {
		return getFirst();
	}

	@Override
	public boolean remove(Object o) {
		NodoSimplementeEnlazado<T> actual = tope, prev = null;
		while (actual != null) {
			if (Objects.equals(actual.getDato(), o)) {
				if (prev == null)
					tope = actual.getSiguiente();
				else
					prev.setSiguiente(actual.getSiguiente());
				tamanio--;
				return true;
			}
			prev = actual;
			actual = actual.getSiguiente();
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		for (T e : this)
			if (Objects.equals(e, o))
				return true;
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[tamanio];
		int i = 0;
		for (T e : this)
			arr[i++] = e;
		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		if (a.length < tamanio)
			a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), tamanio);
		int i = 0;
		for (T e : this)
			a[i++] = (E) e;
		if (a.length > tamanio)
			a[tamanio] = null;
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object e : c)
			if (!contains(e))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T e : c)
			addLast(e);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean mod = false;
		for (Object e : c)
			while (remove(e))
				mod = true;
		return mod;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean mod = false;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			T e = it.next();
			if (!c.contains(e)) {
				it.remove();
				mod = true;
			}
		}
		return mod;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
	    NodoSimplementeEnlazado<T> actual = tope;
	    NodoSimplementeEnlazado<T> prev = null;
	    while (actual != null) {
	        if (Objects.equals(actual.getDato(), o)) {
	            if (prev == null) {
	                // coincide en el primero
	                tope = actual.getSiguiente();
	            } else {
	                prev.setSiguiente(actual.getSiguiente());
	            }
	            tamanio--;
	            return true;
	        }
	        prev = actual;
	        actual = actual.getSiguiente();
	    }
	    return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		NodoSimplementeEnlazado<T> actual = tope;
		NodoSimplementeEnlazado<T> prev = null;

		NodoSimplementeEnlazado<T> lastMatch = null;
		NodoSimplementeEnlazado<T> lastMatchPrev = null;

	    while (actual != null) {
	        if (Objects.equals(actual.getDato(), o)) {
	            lastMatch = actual;
	            lastMatchPrev = prev;
	        }
	        prev = actual;
	        actual = actual.getSiguiente();
	    }

	    if (lastMatch == null) return false;

	    if (lastMatchPrev == null) {
	        // la última coincidencia era el primer nodo
	        tope = lastMatch.getSiguiente();
	    } else {
	        lastMatchPrev.setSiguiente(lastMatch.getSiguiente());
	    }
	    tamanio--;
	    return true;
	}

	@Override
	public boolean add(T e) {
	    addLast(e);
	    return true;
	}
}