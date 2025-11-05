package org.thegoats.rolgar2.util.collections;

import org.junit.jupiter.api.Test;

public class Board3dTest {
    @Test
    public void testIterator() {
        Board3d<Integer> board = new Board3d<>(2, 2, 1);

        System.out.println(board.getRowCount());
        System.out.println(board.getColumnCount());
        System.out.println(board.getLayerCount());

        board.set(0, 0, 0, 10);
        board.set(0, 1, 0, 10);
        board.set(1, 0, 0, 10);
        board.set(1, 1, 0, 10);

        System.out.println("for");
        for (Integer i : board) {
            System.out.println(i);
        }

        System.out.println("forEach");
        board.forEach(System.out::println);
    }

    @Test
    public void testSupplierOnConstructor() {
        System.out.println("Sin supplier");
        Board3d<Integer> board = new Board3d<>(2, 2, 2);
        board.forEach(System.out::println); // sin supplier todos los valores son null por defecto

        System.out.println("Con supplier");
        final int[] i = {0};
        Board3d<Integer> board2 = new Board3d<>(2, 2, 2, () -> i[0]++);
        board2.forEach(System.out::println); // con supplier se puede inicializar valores
    }
}
