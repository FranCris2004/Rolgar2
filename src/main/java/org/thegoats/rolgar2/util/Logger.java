package org.thegoats.rolgar2.util;

/**
 * Interfaz que representa un sistema de logs,
 * donde y como se impriman los mensajes depende de la implementacion.
 */
public abstract class Logger {
    private LogLevel logLevel;

    /**
     * Crea el logger con estado
     * @param logLevel no null
     */
    public Logger(LogLevel logLevel) {
        Assert.notNull(logLevel, "logLevel no puede ser null");
        this.logLevel = logLevel;
    }

    /**
     * Crea el logger con logLevel predeterminado
     */
    public Logger() {
        this(LogLevel.DEFAULT);
    }


    /**
     * @return true si logLevel == LogLevel.WARNING
     */
    public boolean logLevelIsError(){
        return logLevel == LogLevel.ERROR;
    }

    /**
     * @return true si logLevel == LogLevel.INFO
     */
    public boolean logLevelIsInfo(){
        return logLevel == LogLevel.INFO;
    }

    /**
     * @return true si logLevel == LogLevel.DEBUG
     */
    public boolean logLevelIsDebug(){
        return logLevel == LogLevel.DEBUG;
    }

    /**
     * @return true si logLevel == LogLevel.WARNING
     */
    public boolean logLevelIsWarning(){
        return logLevel == LogLevel.WARNING;
    }

    /**
     * @param logLevel no null, distinto al logLevel actual
     */
    public void setLogLevel(LogLevel logLevel) {
        Assert.notNull(logLevel, "logLevel no puede ser null");
        Assert.isTrue(logLevel != this.logLevel, "logLevel debe ser distinto al logLevel actual");
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
