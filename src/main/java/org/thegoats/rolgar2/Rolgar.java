package org.thegoats.rolgar2;

import org.thegoats.rolgar2.game.GameBuilder;
import org.thegoats.rolgar2.util.ConsoleLogger;
import org.thegoats.rolgar2.util.LogLevel;
import org.thegoats.rolgar2.util.io.ConsoleSelection;

import java.util.Random;

public class Rolgar {
    public static void main(String[] args) {
        var logger = new ConsoleLogger(LogLevel.DEBUG);

        logger.logInfo("Iniciando Rolgar 2.");

        var playAgainSelection = new ConsoleSelection<Boolean>()
                .addOption("Si", true)
                .addOption("No", false)
                .maxTries(3)
                .selectionPrompt("¿Desea jugar de nuevo?")
                .selectionRetryMessage("Opcion invalida. Intentelo nuevamente.");

        boolean playAgain = true;
        while (playAgain) {
            GameBuilder.createBuilder()
                    // configura el juego
                    .setLogger(logger)
                    .setRandom(new Random())
                    .selectDifficulty("difficulties")
                    .selectMap("maps")
                    .initPlayers()
                    // construye el juego
                    .build()
                    // ejecuta el juego
                    .run();

            playAgain = playAgainSelection.select().orElse(false);
        }
    }
}
