package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.card.*;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.ConsoleSelection;
import org.thegoats.rolgar2.util.io.Selection;
import org.thegoats.rolgar2.world.Position;
import org.thegoats.rolgar2.world.WorldCell;

import java.util.Arrays;
import java.util.List;

public class GameCharacterPlayerTurnManager extends GameCharacterTurnManager {
    private enum TurnActionEnum {
        RealizarMovimiento,
        UsarCarta,
        AgarrarCarta,
        Atacar,
        GestionarAlianzas;

        public static List<TurnActionEnum> getActions() {
            return Arrays.asList(values());
        }

        private static List<TurnActionEnum> getFreezedActions() {
            return Arrays.asList(values());
        }
    }

    private enum MovementDirectionEnum {
        Norte,
        Oeste,
        Sur,
        Este
    }

    private final Selection<TurnActionEnum> turnSelection;

    private final Selection<TurnActionEnum> freezedTurnSelection;

    private final Selection<MovementDirectionEnum> directionSelection;

    private final Selection<Boolean> pickCardSelection;

    private final Selection<Card> useCardSelection;
    /**
     * Construye el GameCharacterTurnManager
     * @param gameCharacter no null
     */
    public GameCharacterPlayerTurnManager(GameCharacter gameCharacter) {
        super(gameCharacter);
        Assert.isTrue(gameCharacter.isPlayerCharacter(), "gameCharacter debe ser un personaje de un jugador.");

        turnSelection = new ConsoleSelection<TurnActionEnum>()
                .addAllOptions(TurnActionEnum.getActions())
                .maxTries(3)
                .selectionPrompt("¿Cual sera su proxima accion?")
                .selectionRetryMessage("Opcion invalida.");

        freezedTurnSelection = new ConsoleSelection<TurnActionEnum>()
                .addAllOptions(TurnActionEnum.getFreezedActions())
                .maxTries(3)
                .selectionPrompt("¿Cual sera su proxima accion?")
                .selectionRetryMessage("Opcion invalida.");

        directionSelection = new ConsoleSelection<MovementDirectionEnum>()
                .addAllOptions(Arrays.stream(MovementDirectionEnum.values()).toList())
                .maxTries(3)
                .selectionPrompt("¿Hacia que direccion se desea mover?")
                .selectionRetryMessage("Opcion invalida.");

        pickCardSelection = new ConsoleSelection<Boolean>()
                .addOption("Si", true)
                .addOption("No", false)
                .maxTries(3)
                .selectionPrompt("¿Desea tomar la carta?")
                .selectionRetryMessage("Opcion invalida.");

        useCardSelection = new ConsoleSelection<Card>()
                .addAllOptions(gameCharacter.getCharacterData().getDeck().getCards())
                .maxTries(3)
                .selectionPrompt("¿Que carta quiere usar?")
                .selectionRetryMessage("Opcion invalida.");
    }

    /**
     * realiza el turno, deriva a doFreezedTurn() si es necesario
     */
    @Override
    public void doTurn() {
        int remainingMoves = gameCharacter.getCharacterData().getMoves();
        while(remainingMoves >= 0) {
            int layer = gameCharacter.getWorldCell().getPosition().getLayer();
            gameCharacter.getGame().worldViewer.showLayer(gameCharacter.getWorld(), layer);

            logger.logInfo("El jugador " + gameCharacter.getCharacterData().getName() + " realiza su turno.");

            var action = (gameCharacter.getCharacterData().isFreezed() ? freezedTurnSelection.select() : turnSelection.select())
                    .orElseThrow();

            if (doTurnAction(action)) {
                remainingMoves--;
            } else {
                logger.logWarning("La accion no se ha completado.");
            }
        }
    }

    private boolean doTurnAction(TurnActionEnum action) {
        return switch (action) {
            case RealizarMovimiento -> move();
            case Atacar -> atacar();
            case AgarrarCarta -> agarrarCarta();
            case UsarCarta -> usarCarta();
            case GestionarAlianzas -> gestionarAlianzas();
        };
    }

    /**
     * Consulta al usuario en que direccion moverse y realiza el movimiento
     * @return true si se pudo concretar el movimiento
     */
    private boolean move() {
        var direction = directionSelection
                .select();

        if (direction.isEmpty()) {
            logger.logInfo("No se pudo realizar el movimiento.");
            return false;
        }

        var newCell = gameCharacter.getWorld().getCell(
                switch (direction.get()) {
                    case Norte -> northPosition();
                    case Oeste -> westPosition();
                    case Sur -> southPosition();
                    case Este -> eastPosition();
                }
        );

        if(newCell.characterCanMove()) {
            gameCharacter.moveCharacter(newCell.getPosition());
            return true;
        }

        return false;
    }

    private boolean atacar() {
        return false;
    }

    private boolean agarrarCarta() {
        return false;
    }

    private boolean usarCarta() {
        Assert.isTrue(!gameCharacter.getCharacterData().getDeck().isEmpty(), "El jugador tiene que tener cartas");

        useCardSelection.select()
                .ifPresent(card -> {
                    if (card instanceof CardWithCharacterTarget cardWithCharacterTarget) {
                        cardWithCharacterTarget.setTarget(gameCharacter.getCharacterData());
                    }

                    // TODO: agregar lo que requieran stealcard y teleport card

                    if (card instanceof StealingCard stealingCard) {
                        gameCharacter.getGame().logger.logWarning("el uso de StealingCard aun no esta implementado.");
                    }

                    if (card instanceof TeleportCard teleportCard) {
                        gameCharacter.getGame().logger.logWarning("el uso de TeleportCard aun no esta implementado.");
                    }

                    card.use();
                    gameCharacter.getCharacterData().getDeck().remove(card);
                });

        return false;
    }

    private boolean gestionarAlianzas() {
        return false;
    }

    /**
     * Le pregunta al jugador si quiere recoger la carta que esta ubicada en la celda destino
     * @param card no null
     * @return true si se recogio la carta, false si no se recogio
     */
    public boolean pickCard(Card card) {
        Assert.notNull(card, "card no puede ser null");

        var optionalChoice = pickCardSelection.select();

        if (optionalChoice.isPresent() && optionalChoice.get()) {
            gameCharacter.getCharacterData().getDeck().add(card);
            return true;
        }

        return false;
    }

    private Position northPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow() + 1,
                position.getColumn(),
                position.getLayer()
        );
    }

    private Position southPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow() - 1,
                position.getColumn(),
                position.getLayer()
        );
    }

    private Position westPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow(),
                position.getColumn() - 1,
                position.getLayer()
        );
    }

    private Position eastPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow(),
                position.getColumn() + 1,
                position.getLayer()
        );
    }

    /**
     * Dados row, column y layer, construye un Position que está comprendido entre los limites del mapa
     * @param row entero
     * @param column entero
     * @param layer entero
     * @return Position recortada entre 0 y los limites del mapa
     */
    private Position clampedPosition(int row, int column, int layer) {
        var world = gameCharacter.getWorld();
        return new Position(
                Math.clamp(row, 0, world.getRowCount()),
                Math.clamp(column, 0, world.getColumnCount()),
                Math.clamp(layer, 0, world.getLayerCount())
                );
    }
}
