package org.thegoats.rolgar2.util;

public class GameUtils {
    public static boolean validName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9._-]{3,20}$");
    }
}
