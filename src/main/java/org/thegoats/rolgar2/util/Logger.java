package org.thegoats.rolgar2.util;

/**
 * Interfaz que representa un sistema de logs,
 * donde y como se impriman los mensajes depende de la implementacion
 */
public interface Logger {
    /**
     * Imprime un mensaje
     * @param message mensaje a imprimir
     */
    void log(String message);

    /**
     * Imprime un mensaje de error
     * @param message mensaje a imprimir
     */
    void logError(String message);

    /**
     * Imprime un mensaje de advertencia
     * @param message mensaje a imprimir
     */
    void logWarning(String message);
}
