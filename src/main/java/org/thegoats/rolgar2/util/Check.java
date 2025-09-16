package org.thegoats.rolgar2.util;

public class Check {
    /**
     * Un nombre valido contiene solamente de 3 a 20 caracteres alfanumericos, '.', '_' o '-'
     * @param name Nombre a validar
     * @return true si 'name' es valido, false si es null o invalido
     */
    public static boolean validName(String name) {
        if (name == null) {
            return false;
        }

        return name.matches("^[a-zA-Z0-9._-]{3,20}$");
    }
}
