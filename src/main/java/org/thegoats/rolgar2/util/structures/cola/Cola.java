package org.thegoats.rolgar2.util.structures.cola;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

import estructuras.nodos.NodoSimplementeEnlazado;

public class Cola<T> implements Queue<T> {

    private NodoSimplementeEnlazado<T> primero;
    private NodoSimplementeEnlazado<T> ultimo;
    private int tamanio;

    public Cola() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    // Inserta al final de la cola
    @Override
    public boolean offer(T e) {
        NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(e);
        if (ultimo == null) {
            primero = ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        tamanio++;
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
        if (primero == null) return null;
        T dato = primero.getDato();
        primero = primero.getSiguiente();
        if (primero == null) ultimo = null;
        tamanio--;
        return dato;
    }

    // Devuelve y elimina el primero, o lanza excepción si vacío
    @Override
    public T remove() {
        T dato = poll();
        if (dato == null) throw new java.util.NoSuchElementException();
        return dato;
    }

    // Devuelve el primero sin eliminarlo, o null si vacío
    @Override
    public T peek() {
        return (primero == null) ? null : primero.getDato();
    }

    // Devuelve el primero sin eliminarlo, o lanza excepción si vacío
    @Override
    public T element() {
        if (primero == null) throw new java.util.NoSuchElementException();
        return primero.getDato();
    }

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
        primero = ultimo = null;
        tamanio = 0;
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
            NodoSimplementeEnlazado<T> actual = primero;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[tamanio];
        int i = 0;
        for (T elem : this) arr[i++] = elem;
        return arr;
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
        if (a.length > tamanio) a[tamanio] = null;
        return a;
    }

    @Override
    public boolean remove(Object o) {
        if (primero == null) return false;

        if (Objects.equals(primero.getDato(), o)) {
            poll();
            return true;
        }

        NodoSimplementeEnlazado<T> actual = primero;
        while (actual.tieneSiguiente() && !Objects.equals(actual.getSiguiente().getDato(), o)) {
            actual = actual.getSiguiente();
        }

        if (actual.tieneSiguiente()) {
            if (actual.getSiguiente() == ultimo) ultimo = actual;
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            tamanio--;
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
        boolean modificado = false;
        for (T e : c) {
            offer(e);
            modificado = true;
        }
        return modificado;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modificado = false;
        for (Object o : c) {
            while (remove(o)) {
                modificado = true;
            }
        }
        return modificado;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modificado = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (!c.contains(elem)) {
                remove(elem);
                modificado = true;
            }
        }
        return modificado;
    }
}
