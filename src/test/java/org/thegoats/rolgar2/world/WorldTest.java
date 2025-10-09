package org.thegoats.rolgar2.world;

import org.junit.jupiter.api.Test;

public class WorldTest {
    @Test
    public void testWorld() {
        World world = new World(4, 3, 3);

        world.forEach(worldCell -> {
            worldCell.set(new Block(worldCell.hashCode() % 2 == 0));
        });

        world.forEach(System.out::println);
    }
}
