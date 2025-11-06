package org.thegoats.rolgar2.util.structures.stack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;

public class Stack<T> implements Deque<T> {
	private LinkedNode<T> top;
	private int size;

	public Stack() {
		this.top = null;
		this.size = 0;
	}

	// ---- Operaciones principales de Pila ----
	@Override
	public void push(T element) {
		addFirst(element);
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
	public void addFirst(T element) {
        LinkedNode<T> temporary = new LinkedNode<>(element);
		temporary.setNext(top);
		top = temporary;
		size++;
	}

	@Override
	public void addLast(T element) {
		// En una pila no se usa normalmente, pero lo implementamos por contrato
        LinkedNode<T> temporary = new LinkedNode<>(element);
		if (isEmpty()) {
			top = temporary;
		} else {
            LinkedNode<T> actual = top;
			while (actual.getNext() != null) {
				actual = actual.getNext();
			}
			actual.setNext(temporary);
		}
		size++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		T data = top.getData();
		top = top.getNext();
		size--;
		return data;
	}

	@Override
	public T removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		if (top.getNext() == null) {
			T data = top.getData();
			top = null;
			size--;
			return data;
		}
        LinkedNode<T> actual = top;
		while (actual.getNext().getNext() != null) {
			actual = actual.getNext();
		}
		T data = actual.getNext().getData();
		actual.setNext(null);
		size--;
		return data;
	}

	@Override
	public T peekFirst() {
		return (top == null) ? null : top.getData();
	}

	@Override
	public T peekLast() {
		if (isEmpty())
			return null;
        LinkedNode<T> actual = top;
		while (actual.getNext() != null) {
			actual = actual.getNext();
		}
		return actual.getData();
	}

	// ---- Métodos utilitarios ----
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		top = null;
		size = 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
            LinkedNode<T> actual = top;

			@Override
			public boolean hasNext() {
				return actual != null;
			}

			@Override
			public T next() {
				if (actual == null)
					throw new NoSuchElementException();
				T data = actual.getData();
				actual = actual.getNext();
				return data;
			}
		};
	}

	@Override
	public Iterator<T> descendingIterator() {
		// para cumplir contrato, aunque sea O(n)
		List<T> list = new ArrayList<>();
		for (T e : this)
			list.add(e);
		Collections.reverse(list);
		return list.iterator();
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
		return top.getData();
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
        LinkedNode<T> actual = top, prev = null;
		while (actual != null) {
			if (Objects.equals(actual.getData(), o)) {
				if (prev == null)
					top = actual.getNext();
				else
					prev.setNext(actual.getNext());
				size--;
				return true;
			}
			prev = actual;
			actual = actual.getNext();
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
		Object[] arr = new Object[size];
		int i = 0;
		for (T e : this)
			arr[i++] = e;
		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E[] toArray(E[] a) {
		if (a.length < size)
			a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		int i = 0;
		for (T e : this)
			a[i++] = (E) e;
		if (a.length > size)
			a[size] = null;
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
        LinkedNode<T> actual = top;
        LinkedNode<T> prev = null;
	    while (actual != null) {
	        if (Objects.equals(actual.getData(), o)) {
	            if (prev == null) {
	                // coincide en el primero
	                top = actual.getNext();
	            } else {
	                prev.setNext(actual.getNext());
	            }
	            size--;
	            return true;
	        }
	        prev = actual;
	        actual = actual.getNext();
	    }
	    return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
        LinkedNode<T> actual = top;
        LinkedNode<T> prev = null;

        LinkedNode<T> lastMatch = null;
        LinkedNode<T> lastMatchPrev = null;

	    while (actual != null) {
	        if (Objects.equals(actual.getData(), o)) {
	            lastMatch = actual;
	            lastMatchPrev = prev;
	        }
	        prev = actual;
	        actual = actual.getNext();
	    }

	    if (lastMatch == null) return false;

	    if (lastMatchPrev == null) {
	        // la última coincidencia era el primer nodo
	        top = lastMatch.getNext();
	    } else {
	        lastMatchPrev.setNext(lastMatch.getNext());
	    }
	    size--;
	    return true;
	}

	@Override
	public boolean add(T e) {
	    addLast(e);
	    return true;
	}
}