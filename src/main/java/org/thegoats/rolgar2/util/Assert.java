package org.thegoats.rolgar2.util;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Assert {
    /**
     * @param o No null
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'o' es null
     */
    public static void notNull(Object o, String message) {
        if (o == null) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n No cero
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'n' es cero
     */
    public static void nonZero(int n, String message) {
        if (n == 0) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n No negativo
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'n' es negativo
     */
    public static void nonNegative(int n, String message) {
        if (n < 0) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n Positivo
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'n' no es positivo
     */
    public static void positive(int n, String message) {
        if (n <= 0) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n Positivo
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'n' no es positivo
     */
    public static void positive(double n, String message) {
        if (n <= 0) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n Debe ser un valor en el rango de 'a' a 'b'
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si n no es un valor entre 'a' a 'b'
     */
    public static void inRange(int n, int a, int b, String message) {
        int floor = Math.min(a, b);
        int roof = Math.max(a, b);
        if (n < floor || n > roof) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param n Debe ser un valor en el rango de 'a' a 'b'
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si n no es un valor entre 'a' a 'b'
     */
    public static void inRange(double n, double a, double b, String message) {
        Assert.isTrue(a > b, "a > b para rangos");
        if (n < a || n > b) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param a Entero
     * @param b Entero
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'a' es mayor o igual a 'b'
     */
    public static void lowerThan(int a, int b, String message) {
        if (a >= b) {
            throw new RuntimeException(message);
        }
    }

    /**
     * El nombre se valida con util.Check.validName()
     * @param name Nombre valido
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'nombre' es invalido
     */
    public static void validName(String name, String message) {
        if (!GameUtils.validName(name)) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param string No null, No vacío
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'string' es null o vacío
     */
    public static void notNullOrEmpty(String string, String message) {
        if (string == null || string.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    /**
     * @param condition debe ser verdadera
     * @param message mensaje de la excepcion
     * @throws RuntimeException si la condicion era false
     */
    public static void isTrue(boolean condition, String message){
        if(!condition){
            throw new RuntimeException(message);
        }
    }

    public static void fileExists(String path, String message) {
        ClassLoader cl = Assert.class.getClassLoader();

        // Busca el recurso dentro del classpath
        URL url = cl.getResource(path);

        if (url == null) {
            throw new RuntimeException(message);
        }
    }
}
