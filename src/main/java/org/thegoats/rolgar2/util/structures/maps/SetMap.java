
package org.thegoats.rolgar2.util.structures.maps;

import org.thegoats.rolgar2.util.structures.sets.Set;

import java.util.*;

public class SetMap<K, V> implements Map<K, V> {
    private static class SetMapEntry<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        SetMapEntry(K key, V value) {
            this.key = Objects.requireNonNull(key);
            this.value = value;
        }

        @Override public K getKey() { return key; }
        @Override public V getValue() { return value; }

        @Override
        public V setValue(V value) {
            V prev = this.value;
            this.value = value;
            return prev;
        }

        // Igualdad solo por clave, como lo hace la mayoría de las implementaciones internas
        @Override
        public int hashCode() { return Objects.hashCode(key); }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SetMapEntry<?, ?> e)) return false;
            return Objects.equals(key, e.key);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private final Set<Entry<K,V>> entries = new Set<>();

    @Override
    public int size() { return entries.size(); }

    @Override
    public boolean isEmpty() { return entries.isEmpty(); }

    @Override
    public boolean containsKey(Object key) {
        return entries.contains(new SetMapEntry<>( (K) key, null ));
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry<K,V> e : entries) {
            if (Objects.equals(e.getValue(), value)) return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (Entry<K,V> e : entries) {
            if (Objects.equals(e.getKey(), key)) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        SetMapEntry<K,V> newEntry = new SetMapEntry<>(key, value);

        // Si existe una entry con esa key, removerla primero
        for (Entry<K,V> e : entries) {
            if (Objects.equals(e.getKey(), key)) {
                V oldValue = e.getValue();
                entries.remove(e);
                entries.add(newEntry);
                return oldValue;
            }
        }

        // No existe → agregar
        entries.add(newEntry);
        return null;
    }

    @Override
    public V remove(Object key) {
        for (Entry<K,V> e : entries) {
            if (Objects.equals(e.getKey(), key)) {
                V oldValue = e.getValue();
                entries.remove(e);
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K,? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> s = new Set<>();
        for (Entry<K,V> e : entries) s.add(e.getKey());
        return s;
    }

    @Override
    public Collection<V> values() {
        List<V> v = new ArrayList<>();
        for (Entry<K,V> e : entries) v.add(e.getValue());
        return v;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }
}
