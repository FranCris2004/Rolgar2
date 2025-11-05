package org.thegoats.rolgar2.util.structures.conjuntos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Conjunto<E> implements Set<E> {

    private List<E> elementos;

    public Conjunto() {
        this.elementos = new ArrayList<>();
    }

    @Override
    public int size() {
        return elementos.size();
    }

    @Override
    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elementos.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return elementos.iterator();
    }

    @Override
    public Object[] toArray() {
        return elementos.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elementos.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (!elementos.contains(e)) {
            elementos.add(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return elementos.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elementos.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modificada = false;
        for (E e : c) {
            if (add(e)) {
                modificada = true;
            }
        }
        return modificada;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elementos.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elementos.removeAll(c);
    }

    @Override
    public void clear() {
        elementos.clear();
    }
}