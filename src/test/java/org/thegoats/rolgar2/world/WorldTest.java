package org.thegoats.rolgar2.world;

import org.junit.jupiter.api.Test;

public class WorldTest {
    @Test
    public void testWorld() {
        World world = new World(3, 2, 4);

        world.forEach(worldCell -> {
            worldCell.setFloor(new Floor(worldCell.hashCode() % 2 == 0));
        });

        world.forEach(System.out::println);
    }
}
