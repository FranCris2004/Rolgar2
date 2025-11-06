package org.thegoats.rolgar2.util;

/**
 * Logger que imprime en la consola
 */
public final class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void logError(String message) {
        System.err.println("Error: " + message);
    }

    @Override
    public void logWarning(String message) {
        System.out.println("Advertencia: " + message);
    }
}
