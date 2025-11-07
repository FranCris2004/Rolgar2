package org.thegoats.rolgar2.util;

import java.util.Arrays;

/**
 * Facilita la toma de la entrada de un usuario cuando se le quiere dar elegir entre un conjunto de opciones.
 * Puede crearse una instancia de Options y llamar instancia.choose o puede llamarse Options.choose directamente.
 */
public final class Options {
    private String promptMessage;
    private String[] options;
    private String retryMessage;
    private int maxRetries;
    public boolean caseInsensitive;

    /**
     * @param promptMessage No null, no vacío, mensaje a mostrar al pedir la entrada del usuario
     * @param options No null, no vacío, ninguno de los strings que contiene debe ser null o vacío, conjunto de opciones
     * @param retryMessage No null, no vacío, mensaje a ser mostrado en cada reintento
     * @param maxRetries Mayor o igual a cero, cantidad de reintentos antes de devolver null
     * @param caseInsensitive Si la eleccion de opciones sera case sensitive o no
     */
    public Options(String promptMessage, String[] options, String retryMessage, int maxRetries, boolean caseInsensitive) {
        setPromptMessage(promptMessage);
        setOptions(options);
        setRetryMessage(retryMessage);
        this.maxRetries = maxRetries;
        this.caseInsensitive = caseInsensitive;
    }

    /**
     * Versión no estatica de Options.choose
     * @return la opción elegida o null en caso de haberse agotado los intentos
     */
    public String choose() {
        return chooseWithNoValidation(promptMessage, options, retryMessage, maxRetries, caseInsensitive);
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public String[] getOptions() {
        // se devuelve un clon para evitar la modificacion externa de de options
        return options.clone();
    }

    public String getRetryMessage() {
        return retryMessage;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * @param promptMessage No null, no vacío
     */
    public void setPromptMessage(String promptMessage) {
        Assert.notNullOrEmpty(promptMessage, "'promptMessage' debe ser no null y no vacío");
        this.promptMessage = promptMessage;
    }

    /**
     * @param options No null, no vacío, ninguno de los strings que contiene debe ser null o vacío
     */
    public void setOptions(String[] options) {
        Assert.notNull(options, "'options' debe ser no null");
        Assert.positive(options.length, "'options' debe ser no vacío");
        for (String option : options) {
            Assert.notNullOrEmpty(option, "ninguna opción en 'options' debe ser null o vacía");
        }
        this.options = options.clone();
    }

    /**
     * @param retryMessage No null, no vacío
     */
    public void setRetryMessage(String retryMessage) {
        Assert.notNullOrEmpty(promptMessage, "'retryMessage' debe ser no null y no vacío");
        this.retryMessage = retryMessage;
    }

    /**
     * @param maxRetries Mayor o igual a cero
     */
    public void setMaxRetries(int maxRetries) {
        Assert.positive(maxRetries, "'maxRetries' debe ser mayor o igual a cero");
        this.maxRetries = maxRetries;
    }

    /**
     * Versón estática de choose
     * @param promptMessage No null, no vacío
     * @param options No null, no vacío, ninguno de los strings que contiene debe ser null o vacío
     * @param retryMessage No null, no vacío
     * @param maxRetries Mayor o igual a cero
     * @return la opción elegida o null en caso de haberse agotado los intentos
     */
    public static String choose(
            String promptMessage, String[] options, String retryMessage, int maxRetries, boolean caseInsensitive
    ) {
        validateStaticChooseArguments(promptMessage, options, retryMessage, maxRetries);
        return chooseWithNoValidation(promptMessage, options, retryMessage, maxRetries, caseInsensitive);
    }

    //
    // Los siguientes metodos son puramente auxiliares
    //

    /**
     * Contiene la funcionalidad en común entre choose estatico y no estatico, no valida nada
     */
    private static String chooseWithNoValidation(
            String promptMessage, String[] options, String retryMessage, int maxRetries, boolean caseInsensitive
    ) {
        for (int tryCount = 0; tryCount <= maxRetries; tryCount++) {
            System.out.print(promptMessage + " " + Arrays.toString(options) + ": ");
            String choice = Keyboard.readString();

            if (validChoice(choice, options, caseInsensitive)) {
                return choice;
            }

            System.out.println(retryMessage);
        }

        return null;
    }

    /**
     * @param promptMessage No null, no vacío
     * @param options No null, no vacío, ninguno de los strings que contiene debe ser null o vacío
     * @param retryMessage No null, no vacío
     * @param maxRetries Mayor o igual a cero
     */
    private static void validateStaticChooseArguments(
            String promptMessage, String[] options, String retryMessage, int maxRetries) {
        Assert.notNullOrEmpty(promptMessage, "'promptMessage' debe ser no null y no vacío");
        Assert.notNullOrEmpty(retryMessage, "'retryMessage' debe ser no null y no vacío");
        Assert.positive(maxRetries, "'maxRetries' debe ser mayor o igual a cero");
        Assert.notNull(options, "'options' debe ser no null");
        Assert.positive(options.length, "'options' debe ser no vacío");
        for (String option : options) {
            Assert.notNullOrEmpty(option, "ninguna opción en 'options' debe ser null o vacía");
        }
    }

    /**
     * Valida que choice se encuentre en options, ya sea case sensitive o case insensitive
     */
    private static boolean validChoice(String choice, String[] options, boolean caseInsensitive) {
        // comprueba que choice se encuentre en options
        // usando equalsIgnoreCase o equals si caseInsensitive es true o false respectivamente
        return Arrays.stream(options).anyMatch(caseInsensitive ? choice::equalsIgnoreCase : choice::equals);
    }
}
