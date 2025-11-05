package org.thegoats.rolgar2.world;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class PositionTest {
    @Test
    void validPosition() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    Position position = new Position(i, j, k);
                }
            }
        }
    }

    @Test
    void invalidPosition() {
        mustThrow(() -> {
            Position position = new Position(-1, -1, -1);
        }, "Se permitio construir una posicion invalida");
    }

    @Test
    void isAdjacent() {

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {

                }
            }
        }

        Position position0 = new Position(0, 0, 0);
        Position position1 = new Position(1, 1, 1);
        assert position0.isAdjacent(position1);
    }

    @Test
    void equals() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    Position position0 = new Position(i, j, k);
                    Position position1 = new Position(i, j, k);
                    assert position0.equals(position1);
                }
            }
        }
    }

    @Test
     void toStringTest(){
        Position position1 = new Position(1, 2, 3);
        assert position1.toString().equals("Position[row=1,column=2,layer=3]");

        Position position2 = new Position(12, 12, 12);
        assert position2.toString().equals("Position[row=12,column=12,layer=12]");

        Random random = new Random(System.nanoTime());

        int row = random.nextInt(100);
        int column = random.nextInt(100);
        int layer = random.nextInt(100);

        Position position3 = new Position(row, column, layer);

        String expectedString = "Position[row="+ row +", column="+ column +", layer="+ layer +"]";

    }

    static void mustThrow(Runnable r, String message) {
        boolean error = false;

        try {
            r.run();
        } catch (Exception e) {
            error = true;
        }

        if (!error) {
            throw new RuntimeException(message);
        }
    }
}
