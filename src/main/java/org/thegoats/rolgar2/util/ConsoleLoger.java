package org.thegoats.rolgar2.util;

public final class ConsoleLoger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
