package org.thegoats.rolgar2.util;

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
    public static void nonNegative(long n, String message) {
        if (n < 0) {
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
     * El nombre se valida con util.Check.validName()
     * @param name Nombre valido
     * @param message Mensaje en caso de excepcion
     * @throws RuntimeException Si 'nombre' es invalido
     */
    public static void validName(String name, String message) {
        if (!Check.validName(name)) {
            throw new RuntimeException(message);
        }
    }
}
