package org.thegoats.rolgar2.util;

/**
 * Logger que imprime en la consola
 */
public final class ConsoleLogger extends Logger {
    @Override
    public void logInfo(String message) {
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

    @Override
    public void logDebug(String message) {
        System.out.println("Debug: " + message);
    }
}
