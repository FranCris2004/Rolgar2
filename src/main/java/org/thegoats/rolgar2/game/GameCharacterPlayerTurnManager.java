package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Options;
import java.util.Arrays;

public class GameCharacterPlayerTurnManager extends GameCharacterTurnManager {
    public GameCharacterPlayerTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(gameCharacter.isPlayerCharacter(), "gameCharacter debe ser un personaje de un jugador.");
    }

    @Override
    public void doTurn() {
        gameCharacter.game.logger.logDebug("El jugador " + gameCharacter.characterData.getName() + " realiza su turno.");
        Options turnOptions = new Options("¿Cual sera su próxima acción?",
                new String[]{"Movimiento", "Carta", "Alianza"},
                "Opcion inválida",
                3,
                true);
        System.out.println(Arrays.toString(turnOptions.getOptions()));
        turnOptions.choose().ifPresent(choice -> {
            switch (choice) {
                case "movimiento":
           //         move();
                    break;
                case "carta":
           //         useCards();
                    break;
                case "alianza":
            //        doAlliance();
                    break;
            }
        });
    }

    private void moveCharacter(){
        Options directionsOptions = new Options("¿Hacia que direccion se desea mover?",
                new String[]{"w", "a", "s", "d", "wa", "wd", "sa", "sd"},
                "Direccion inválida",
                3,
                true
                );
        directionsOptions.choose().ifPresent(choice ->{
            switch (choice) {
                case "w":
                    // mover para el norte

                    break;
                case "a":
                    // mover para el oeste
                    break;
                case "s":
                    // mover para el sur
                    break;
                case "d":
                    // mover para el este
                    break;
                case "wa":
                    // mover para el noroeste
                    break;
                case "wd":
                    // mover para el noreste
                    break;
                case "sa":
                    // mover para el sudoeste
                    break;
                case "sd":
                    // mover para el sudeste
                    break;
            }
        });
        // pregunta que quiere hacer y da opciones
        // toma el input del usuario
        // verifica que el input sea valido:
            // 1. que esté entre las opciones
            // 2. que sea valida la accion
        // ejecuta la accion ya sea consumir carta, moverse o atacar

    }

}
