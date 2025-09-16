package org.thegoats.rolgar2.util;
import java.util.Scanner;

public class Keyboard {
    private static Scanner keyboard = new Scanner(System.in);

    /**
     * Inicializa el teclaco
     */
    public static void initialize() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Escanea un entero
     * @return Int
     */
    public static int readInteger() {
        return keyboard.nextInt();
    }

    /**
     * Escanea un string hasta el enter
     * @return String
     */
    public static String readString() {
        return keyboard.nextLine();
    }

    /**
     * Escanea el primer caracter ascii ingresado por el usuario
     * @return char
     */
    public static char readCharacter() {
        return keyboard.next().charAt(0);
    }

    /**
     * Cierra el keyboard y ya no puede volver a abrirse
     */
    public static void close() {
        keyboard.close();
    }
}
