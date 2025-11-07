package org.thegoats.rolgar2.util;

public enum LogLevel {
    INFO, // logs del flujo normal de la aplicacion
    ERROR, // logs de error (cuando se rompe el flujo de ejecucion de la aplicacion y no se puede retomar)
    WARNING, // logs de advertencia (cuando hay algun problema pero aun asi la ejecucion de la aplicacion puede continuar)
    DEBUG; // logs de debug (se deberian habilitar solamente en la etapa de debugging)

    public static final LogLevel DEFAULT = ERROR;
}
