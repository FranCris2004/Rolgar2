package org.thegoats.rolgar2.util;

/**
 * Interfaz que representa un sistema de logs,
 * donde y como se impriman los mensajes depende de la implementacion.
 */
public abstract class Logger {
    private LogLevel logLevel;

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Logger() {
        this(LogLevel.DEFAULT);
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Imprime un mensaje
     * @param message mensaje a imprimir
     */
    public void log(LogLevel messageLevel, String message) {
        if (logLevel.compareTo(messageLevel) <= 0) {
            switch (messageLevel) {
                case INFO: logInfo(message); break;
                case ERROR: logError(message); break;
                case WARNING: logWarning(message); break;
                case DEBUG: logDebug(message); break;
            }
        }
    }

    /**
     * Imprime un mensaje informativo
     * @param message mensaje a imprimir
     */
    public abstract void logInfo(String message);

    /**
     * Imprime un mensaje de error
     * @param message mensaje a imprimir
     */
    public abstract void logError(String message);

    /**
     * Imprime un mensaje de advertencia
     * @param message mensaje a imprimir
     */
    public abstract void logWarning(String message);

    /**
     * Imprime un mensaje de debug
     * @param message mensaje a imprimir
     */
    public abstract void logDebug(String message);
}
