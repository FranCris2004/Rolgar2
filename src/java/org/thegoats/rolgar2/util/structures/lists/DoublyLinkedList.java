package org.thegoats.rolgar2.util.structures.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.thegoats.rolgar2.util.structures.nodes.DoublyLinkedNode;

public class DoublyLinkedList<T> implements List<T> {
    private DoublyLinkedNode<T> first = null;
    private DoublyLinkedNode<T> last = null;
    private int size = 0;

    public DoublyLinkedList() {}

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T element) {
        DoublyLinkedNode<T> temporary = new DoublyLinkedNode<>(element);
        if (last == null) {
            first = last = temporary;
        } else {
            last.setNext(temporary);
            temporary.setPrevious(last);
            last = temporary;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        DoublyLinkedNode<T> temporary = new DoublyLinkedNode<>(element);

        if (index == size) {
            add(element);
            return;
        }
        if (index == 0) {
            temporary.setNext(first);
            if (first != null) first.setPrevious(temporary);
            first = temporary;
            if (last == null) last = temporary;
        } else {
            DoublyLinkedNode<T> actual = getNode(index);
            DoublyLinkedNode<T> previous = actual.getPrevious();
            temporary.setPrevious(previous);
            temporary.setNext(actual);
            previous.setNext(temporary);
            actual.setPrevious(temporary);
        }
        size++;
    }

    @Override
    public T get(int index) {
        return getNode(index).getData();
    }

    @Override
    public T set(int index, T element) {
        DoublyLinkedNode<T> node = getNode(index);
        T old = node.getData();
        node.setData(element);
        return old;
    }

    @Override
    public T remove(int index) {
        DoublyLinkedNode<T> node = getNode(index);
        T data = node.getData();

        DoublyLinkedNode<T> previous = node.getPrevious();
        DoublyLinkedNode<T> next = node.getNext();

        if (previous != null) {
            previous.setNext(next);
        } else {
            first = next;
        }

        if (next != null) {
            next.setPrevious(previous);
        } else {
            last = previous;
        }

        size--;
        return data;
    }

    @Override
    public boolean remove(Object o) {
        DoublyLinkedNode<T> actual = first;
        while (actual != null) {
            if (Objects.equals(actual.getData(), o)) {
                DoublyLinkedNode<T> previous = actual.getPrevious();
                DoublyLinkedNode<T> next = actual.getNext();

                if (previous != null) previous.setNext(next);
                else first = next;

                if (next != null) next.setPrevious(previous);
                else last = previous;

                size--;
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    private DoublyLinkedNode<T> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        DoublyLinkedNode<T> actual;
        if (index < size / 2) {
            actual = first;
            for (int i = 0; i < index; i++) actual = actual.getNext();
        } else {
            actual = last;
            for (int i = size - 1; i > index; i--) actual = actual.getPrevious();
        }
        return actual;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            DoublyLinkedNode<T> actual = first;

            public boolean hasNext() {
                return actual != null;
            }

            public T next() {
                if (actual == null) throw new NoSuchElementException();
                T data = actual.getData();
                actual = actual.getNext();
                return data;
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
        int i = size - 1;
        DoublyLinkedNode<T> actual = last;
        while (actual != null) {
            if (Objects.equals(actual.getData(), o)) return i;
            actual = actual.getPrevious();
            i--;
        }
        return -1;
    }
    @Override public Object[] toArray() { Object[] arr = new Object[size]; int i=0; for(T e: this) arr[i++] = e; return arr; }
    @Override @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        if (a.length < size) a = (E[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        int i=0; for(T e: this) a[i++] = (E)e;
        if (a.length > size) a[size] = null;
        return a;
    }
    @Override public boolean addAll(Collection<? extends T> c) { boolean mod=false; for(T e: c) {add(e); mod=true;} return mod; }
    @Override public boolean addAll(int index, Collection<? extends T> c) { int i=index; boolean mod=false; for(T e:c){ add(i++,e); mod=true;} return mod; }
    @Override public boolean removeAll(Collection<?> c) { boolean mod=false; for(Object o:c) while(remove(o)) mod=true; return mod; }
    @Override public boolean retainAll(Collection<?> c) { boolean mod=false; Iterator<T> it=iterator(); while(it.hasNext()){ if(!c.contains(it.next())){it.remove(); mod=true;}} return mod; }
    @Override public List<T> subList(int fromIndex, int toIndex) { 
        if(fromIndex<0||toIndex> size ||fromIndex>toIndex) throw new IndexOutOfBoundsException();
        DoublyLinkedList<T> sub = new DoublyLinkedList<>();
        DoublyLinkedNode<T> actual = getNode(fromIndex);
        for(int i=fromIndex;i<toIndex;i++){ sub.add(actual.getData()); actual=actual.getNext(); }
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
            DoublyLinkedNode<T> actual = first;
            DoublyLinkedNode<T> lastReturned = null;
            int index = 0;

            public boolean hasNext() { return actual != null; }

            public T next() {
                if (actual == null) throw new NoSuchElementException();
                lastReturned = actual;
                T data = actual.getData();
                actual = actual.getNext();
                index++;
                return data;
            }

            public boolean hasPrevious() { return (actual != null && actual.getPrevious() != null) || (actual == null && last != null); }

            public T previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                if (actual == null) { // estamos al final
                    actual = last;
                    index = size - 1;
                } else {
                    actual = actual.getPrevious();
                    index--;
                }
                lastReturned = actual;
                return actual.getData();
            }

            public int nextIndex() { return index; }
            public int previousIndex() { return index - 1; }

            public void remove() {
                if (lastReturned == null) throw new IllegalStateException();
                DoublyLinkedList.this.remove(index - 1);
                lastReturned = null;
            }

            public void set(T e) {
                if (lastReturned == null) throw new IllegalStateException();
                lastReturned.setData(e);
            }

            public void add(T e) {
                DoublyLinkedList.this.add(index, e);
                index++;
                lastReturned = null;
            }
        };
	}
	
	public void addSorted(T element) {
	    if (!(element instanceof Comparable)) {
	        throw new IllegalArgumentException("El elemento no implementa Comparable");
	    }

	    @SuppressWarnings("unchecked")
	    Comparable<? super T> cmp = (Comparable<? super T>) element;

        DoublyLinkedNode<T> temporary = new DoublyLinkedNode<>(element);

	    if (first == null) { // lista vacía
	        first = last = temporary;
	    } else if (cmp.compareTo(first.getData()) <= 0) { // insertar al inicio
	        temporary.setNext(first);
	        first.setPrevious(temporary);
	        first = temporary;
	    } else if (cmp.compareTo(last.getData()) >= 0) { // insertar al final
	        last.setNext(temporary);
	        temporary.setPrevious(last);
	        last = temporary;
	    } else { // insertar en el medio
            DoublyLinkedNode<T> actual = first;
	        while (actual != null && cmp.compareTo(actual.getData()) > 0) {
	            actual = actual.getNext();
	        }
            DoublyLinkedNode<T> previous = actual.getPrevious();
	        temporary.setPrevious(previous);
	        temporary.setNext(actual);
	        previous.setNext(temporary);
	        actual.setPrevious(temporary);
	    }
	    size++;
	}
}

