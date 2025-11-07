package org.thegoats.rolgar2.util.structures.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.thegoats.rolgar2.util.structures.nodes.LinkedNode;

public class Queue<T> implements java.util.Queue<T> {

    private LinkedNode<T> first;
    private LinkedNode<T> last;
    private int size;

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    // Inserta al final de la cola
    @Override
    public boolean offer(T e) {
        LinkedNode<T> temporary = new LinkedNode<>(e);
        if (last == null) {
            first = last = temporary;
        } else {
            last.setNext(temporary);
            last = temporary;
        }
        size++;
        return true;
    }

    // Igual que offer, requerido por Collection
    @Override
    public boolean add(T e) {
        return offer(e);
    }

    // Devuelve y elimina el primero, o null si vacío
    @Override
    public T poll() {
        if (first == null) return null;
        T data = first.getData();
        first = first.getNext();
        if (first == null) last = null;
        size--;
        return data;
    }

    // Devuelve y elimina el primero, o lanza excepción si vacío
    @Override
    public T remove() {
        T data = poll();
        if (data == null) throw new java.util.NoSuchElementException();
        return data;
    }

    // Devuelve el primero sin eliminarlo, o null si vacío
    @Override
    public T peek() {
        return (first == null) ? null : first.getData();
    }

    // Devuelve el primero sin eliminarlo, o lanza excepción si vacío
    @Override
    public T element() {
        if (first == null) throw new java.util.NoSuchElementException();
        return first.getData();
    }

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
        first = last = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T elem : this) {
            if (Objects.equals(elem, o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            LinkedNode<T> actual = first;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T data = actual.getData();
                actual = actual.getNext();
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int i = 0;
        for (T elem : this) arr[i++] = elem;
        return arr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (T elem : this) {
            a[i++] = (E) elem;
        }
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public boolean remove(Object o) {
        if (first == null) return false;

        if (Objects.equals(first.getData(), o)) {
            poll();
            return true;
        }

        LinkedNode<T> actual = first;
        while (actual.hasNext() && !Objects.equals(actual.getNext().getData(), o)) {
            actual = actual.getNext();
        }

        if (actual.hasNext()) {
            if (actual.getNext() == last) last = actual;
            actual.setNext(actual.getNext().getNext());
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c) {
            offer(e);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            while (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (!c.contains(elem)) {
                remove(elem);
                modified = true;
            }
        }
        return modified;
    }
}
