package org.thegoats.rolgar2.world;

import org.junit.jupiter.api.Test;

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

    @Test void equalsNull() {
        mustThrow(() -> {
            Position position = new Position(0, 0, 0);
            System.out.println("a");
            position.equals(null);
            System.out.println("b");
        }, "Se permitio pasar null a position.equals");
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
