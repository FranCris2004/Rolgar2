package org.thegoats.rolgar2;

import org.thegoats.rolgar2.rolgar.GameBuilder;
import org.thegoats.rolgar2.util.ConsoleLogger;

public class Rolgar {
    public static void main(String[] args) {
        System.out.println("Rolgar 2");

        GameBuilder gameBuilder = new GameBuilder();
        gameBuilder
                .setLogger(new ConsoleLogger())
                .loadDifficulties("./assets/difficulties/")
                .loadMaps("./assets/maps/")
                .build()
                .run();
    }
}
