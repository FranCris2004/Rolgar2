package org.thegoats.rolgar2;

import org.thegoats.rolgar2.game.GameBuilder;
import org.thegoats.rolgar2.util.ConsoleLogger;
import org.thegoats.rolgar2.util.LogLevel;
import org.thegoats.rolgar2.util.Options;

import java.io.IOException;

public class Rolgar {
    public static void main(String[] args) {
        var logger = new ConsoleLogger(LogLevel.DEBUG);

        logger.logInfo("Iniciando Rolgar 2.");

        var playAgainPrompt = new Options(
                "Jugar de nuevo?",
                new String[]{"si", "no"},
                "Opcion invalida, intentelo nuevamente",
                3,
                false
                );

        logger.logDebug("playAgainPrompt: " + playAgainPrompt);

        boolean playAgain = true;
        while (playAgain) {
            try {
                GameBuilder.createBuilder()
                        // configura el juego
                        .setLogger(logger)
                        .selectDifficulty("difficulties")
                        .selectMap("maps")
                        .initPlayers()
                        // construye el juego
                        .build()
                        // ejecuta el juego
                        .run();
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(e);
            }

            playAgain = playAgainPrompt.choose().orElse("no").equals("si");
        }
    }
}
