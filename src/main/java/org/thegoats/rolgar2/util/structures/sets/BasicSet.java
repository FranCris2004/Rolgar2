package org.thegoats.rolgar2.util.structures.sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicSet<E> {

    private List<E> elements;

    public BasicSet() {
        this.elements = new ArrayList<>();
    }


    public boolean add(E element) {
        if (!elements.contains(element)) {
            elements.add(element);
            return true;
        }
        return false;
    }

    public boolean remove(E element) {
        return elements.remove(element);
    }

    public boolean contains(E element) {
        return elements.contains(element);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void empty() {
        elements.clear();
    }

    public BasicSet<E> union(BasicSet<E> other) {
    	BasicSet<E> result = new BasicSet<>();
        for (E e : this.elements) {
            result.add(e);
        }
        for (E e : other.elements()) {
            result.add(e);
        }
        return result;
    }

    public BasicSet<E> intersection(BasicSet<E> other) {
    	BasicSet<E> result = new BasicSet<>();
        for (E e : this.elements) {
            if (other.contains(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public BasicSet<E> difference(BasicSet<E> other) {
    	BasicSet<E> result = new BasicSet<>();
        for (E e : this.elements) {
            if (!other.contains(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public Collection<E> elements() {
        return new ArrayList<>(elements);
    }
}