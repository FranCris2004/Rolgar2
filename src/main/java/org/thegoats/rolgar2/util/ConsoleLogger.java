package org.thegoats.rolgar2.util;

/**
 * Logger que imprime en la consola
 */
public final class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
