package org.thegoats.rolgar2.util;

public final class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
