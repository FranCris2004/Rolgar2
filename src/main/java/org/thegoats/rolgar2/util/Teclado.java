package org.thegoats.rolgar2.util;
import java.util.Scanner;

public class Teclado {
    private static Scanner teclado;

    /**
     * Inicializa el teclado
     */
    public static void inicializar() {
        teclado = new Scanner(System.in);
    }

    /**
     * Escanea un entero
     * @return Int
     */
    public static int leerEntero() {
        return teclado.nextInt();
    }

    /**
     * Escanea un string hasta el enter
     * @return String
     */
    public static String leerString() {
        return teclado.nextLine();
    }

    /**
     * Escanea el primer caracter ascii ingresado por el usuario
     * @return char
     */
    public static char leerCaracter() {
        return teclado.next().charAt(0);
    }

    /**
     * Cierra el teclado y ya no puede volver a abrirse
     */
    public static void finalizar() {
        teclado.close();
    }
}
