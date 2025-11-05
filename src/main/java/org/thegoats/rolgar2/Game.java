package org.thegoats.rolgar2;

import org.thegoats.rolgar2.game.GameCharacter;

import java.util.LinkedList;
import java.util.List;

public final class Game {
    private int turnCount = 0;

    private final List<GameCharacter> gameCharacters = new LinkedList<>();

    public Game() {}

    /**
     * Inicia la ejecucion del juego, esto implica:
     * Cargar las configuraciones y mapas
     * Realizar las impresiones de inicio de juego
     * Iniciar el bucle de juego
     * Realizar las impresiones de fin del juego
     */
    public void run()
    {
        System.out.println("Game run");
        loop();
    }

    /**
     * Bucle de juego en el cual se realizan los turnos y las impresiones iterativas
     */
    private void loop()
    {
        System.out.println("Game loop");
        while (hasNextTurn()) {
            nextTurn();
        }
    }

    /**
     * Evalua si debe haber un proximo turno o si el juego debe terminar
     * @return trues si hay un proximo turno, false si no lo hay
     */
    private boolean hasNextTurn()
    {
        // en el futuro debera comprobar las condiciones para que el juego siga corriendo
        return turnCount < 5;
    }

    /**
     * Todos los personajes hacen su turno
     */
    private void nextTurn()
    {
        turnCount++;

        System.out.println("Turn " + turnCount);

        for (GameCharacter gameCharacter : gameCharacters) {
            if (gameCharacter.isPlayer)
            {
                playerDoTurn(gameCharacter);
            } else {
                enemyDoTurn(gameCharacter);
            }
        }
    }

    private void playerDoTurn(GameCharacter gameCharacter)
    {
        System.out.println("El jugador " + gameCharacter.characterData.getName() + " realiza su turno.");
    }

    private void enemyDoTurn(GameCharacter gameCharacter)
    {
        System.out.println("El enemigo " + gameCharacter.characterData.getName() + " realiza su turno.");
    }
}
