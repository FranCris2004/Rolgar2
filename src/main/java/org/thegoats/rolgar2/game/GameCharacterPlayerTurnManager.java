package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Options;
import org.thegoats.rolgar2.world.Position;
import org.thegoats.rolgar2.world.WorldCell;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class GameCharacterPlayerTurnManager extends GameCharacterTurnManager {
    private final Options turnOptions = new Options("¿Cual sera su próxima acción?",
            new String[]{"Movimiento", "Carta", "Alianza"},
            "Opcion inválida",
            3,
            true);
    private final Options freezedTurnOptions = new Options("¿Cual sera su próxima acción?",
            new String[]{"Carta", "Alianza"},
            "Opcion inválida",
            3,
            true);
    private final Options directionsOptions = new Options("¿Hacia que direccion se desea mover?",
            new String[]{"w", "a", "s", "d", "wa", "wd", "sa", "sd"},
            "Direccion inválida",
            3,
            true);
    private final Options pickCardOptions = new Options("¿Desea tomar la carta?",
            new String[]{"Si", "No"},
            "Opcion inválida",
            3,
            true);
    private final Options useCardOptions = new Options("¿Qué carta quiere usar?",
            gameCharacter.getCharacterData().getDeck().getCardNames(),
            "Opción inválida",
            3,
            true);

    /**
     * Construye el GameCharacterTurnManager
     * @param gameCharacter no null
     */
    public GameCharacterPlayerTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(gameCharacter.isPlayerCharacter(), "gameCharacter debe ser un personaje de un jugador.");
    }

    /**
     * realiza el turno, deriva a doFreezedTurn() si es necesario
     */
    @Override
    public void doTurn() {
        int actualMoves = gameCharacter.getCharacterData().getMoves();
        while(actualMoves >= 0) {
            if (gameCharacter.getCharacterData().isFreezed()) {
                doFreezedTurn();
                actualMoves--;
                continue;
            }

            System.out.println(gameCharacter.getCharacterData().getEffects());
            gameCharacter.getGame().logger.logDebug("El jugador " + gameCharacter.getCharacterData().getName() + " realiza su turno.");

            turnOptions.choose().ifPresent(choice -> {
                switch (choice.toLowerCase()) {
                    case "movimiento":
                        int moveTries = 3;
                        while(!move() && moveTries > 0){
                            moveTries--;
                        }
                        break;
                    case "carta":
                        int cardTries = 3;
                        if(gameCharacter.getCharacterData().getDeck().isEmpty()){
                            break;
                        }
                        while(!useCard() && cardTries > 0){
                            cardTries--;
                        };
                        //         useCards();
                        break;
                    case "alianza":
                        //        doAlliance();
                        break;
                }
            });
            actualMoves--;
        }
    }

    /**
     * Realiza el turno del personaje en caso de estar congelado
     */
    public void doFreezedTurn() {
        int actualMoves = gameCharacter.getCharacterData().getMoves();
        while(actualMoves >= 0) {
            System.out.println(gameCharacter.getCharacterData().getEffects());
            gameCharacter.getGame().logger.logDebug("El jugador " + gameCharacter.getCharacterData().getName() + " realiza su turno.");
            freezedTurnOptions.choose().ifPresent(choice -> {
                switch (choice.toLowerCase()) {
                    case "carta":
                        //         useCards();
                        break;
                    case "alianza":
                        //        doAlliance();
                        break;
                }
            });
            actualMoves--;
        }
    }


    public boolean useCard(){
        Assert.isTrue(!gameCharacter.getCharacterData().getDeck().isEmpty(), "El jugador tiene que tener cartas");
        var optionalChoice = useCardOptions.choose();
        if(optionalChoice.isPresent()){
            var choice = optionalChoice.get();


    }

    /**
     * Consulta al usuario en que direccion moverse y realiza el movimiento
     * @return true si se pudo concretar el movimiento
     */
    private boolean move(){
        var optionalChoice = directionsOptions.choose();
        if(optionalChoice.isPresent()){
            var choice = optionalChoice.get();
            Position oldPosition = gameCharacter.getWorldCell().getPosition();
            Position newPosition;
            switch (choice) {
                case "w":
                    newPosition = clampedPosition(oldPosition.getRow(), oldPosition.getColumn() + 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "a":
                    newPosition = clampedPosition(oldPosition.getRow() - 1, oldPosition.getColumn(), oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "s":
                    newPosition = clampedPosition(oldPosition.getRow(), oldPosition.getColumn() - 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "d":
                    newPosition = new Position(oldPosition.getRow() + 1, oldPosition.getColumn(), oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "wa":
                    newPosition = new Position(oldPosition.getRow() - 1, oldPosition.getColumn() + 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "wd":
                    newPosition = new Position(oldPosition.getRow() + 1, oldPosition.getColumn() + 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "sa":
                    newPosition = new Position(oldPosition.getRow() - 1, oldPosition.getColumn() - 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
                case "sd":
                    newPosition = new Position(oldPosition.getRow() + 1, oldPosition.getColumn() - 1, oldPosition.getLayer());
                    if(!newPosition.equals(oldPosition)){
                        return moveToCell(gameCharacter.getWorld().getCell(newPosition), gameCharacter.getWorldCell());
                    }
                    return false;
            }
        }
        throw new RuntimeException("No se pudo realizar el movimiento");
    }

    /**
     * Dada una celda, comprende los casos de movimientos del jugador hacia ella, y lo mueve cuando corresponde.
     * @param newCell no null, Celda destino
     * @param oldCell no null, Celda origen
     * @return true si se pudo realizar el movimiento, false si no se pudo realizar
     * @throws RuntimeException si ocurre un error inesperado
     */
    private boolean moveToCell(WorldCell newCell, WorldCell oldCell){
        Assert.notNull(newCell, "NewCell no puede ser null");
        Assert.notNull(oldCell, "OldCell no puede ser null");
        Assert.isTrue(!oldCell.equals(newCell), "newCell y oldCell deben ser distintas");
        // DESTINOS CAMINABLES
        if(newCell.isWalkable()){ // Si la celda es caminable
            if(newCell.isFree()){// Si la celda esta vacia
                gameCharacter.moveCharacter(newCell.getPosition());
                return true;
            }
            if(newCell.hasCharacter()){ // Si la celda tiene un personaje o enemigo lo ataca
                CharacterData other = newCell.getCharacter().get().getCharacterData();
                other.takeDamage(gameCharacter.getCharacterData().getStrength());
                return true;
            }
            if(newCell.hasWall()) {
                if (newCell.hasClimbableWall()) { // Si la celda tiene una pared escalable
                    Optional<WorldCell> upperNeighbor = newCell.getUpperNeighbor();
                    if (upperNeighbor.isPresent()) {
                        return moveToCell(upperNeighbor.get(), oldCell); // intenta mover a la celda de arriba
                    }
                }
                return false; // si la celda tiene una pared no caminable
            }
            if(newCell.hasCard()){
                if(!gameCharacter.getCharacterData().getDeck().isFull()){
                    if(pickCard(newCell.getCard().get())){
                        newCell.setCard(null);
                    }
                }
                gameCharacter.moveCharacter(newCell.getPosition());
                return true;
            }
        }
        throw new RuntimeException("No se pudo mover el personaje");
    }

    /**
     * Le pregunta al jugador si quiere recoger la carta que esta ubicada en la celda destino
     * @param card no null
     * @return true si se recogio la carta, false si no se recogio
     */
    public boolean pickCard(Card card) {
        Assert.notNull(card, "card no puede ser null");
        var optionalChoice = pickCardOptions.choose();
        if (optionalChoice.isPresent()) {
            var choice = optionalChoice.get();
            switch (choice.toLowerCase()) {
                case "si":
                    gameCharacter.getCharacterData().getDeck().add(card);
                    return true;
                case "no":
                    return false;
            }
        }
        return false;
    }

    /**
     * Dados row, column y layer, construye un Position que está comprendido entre los limites del mapa
     * @param row entero
     * @param column entero
     * @param layer entero
     * @return Position recortada entre 0 y los limites del mapa
     */
    public Position clampedPosition(int row, int column, int layer){
        return new Position(
                Math.clamp(row, 0, gameCharacter.getWorld().getRowCount()),
                Math.clamp(column, 0, gameCharacter.getWorld().getColumnCount()),
                Math.clamp(layer, 0, gameCharacter.getWorld().getLayerCount())
                );
    }
}
