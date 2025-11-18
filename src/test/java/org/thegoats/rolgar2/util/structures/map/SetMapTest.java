package org.thegoats.rolgar2.util.structures.map;

import org.junit.jupiter.api.Test;
import org.thegoats.rolgar2.util.structures.maps.SetMap;

import java.util.Map;

public class SetMapTest {
    @Test
    public void test1() {
        String word = "Hello, World! This is a test!";
        Map<Character, Integer> counts = new SetMap<>();

        for (Character c : word.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        System.out.println("Word: " + word);
        System.out.println("lenght(): " + word.length());

        System.out.println("\nCounts: ");
        System.out.println("size(): " + counts.size());

        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
