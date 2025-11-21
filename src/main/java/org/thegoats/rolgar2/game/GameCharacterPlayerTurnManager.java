package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.card.*;
import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.ConsoleSelection;
import org.thegoats.rolgar2.util.io.Selection;
import org.thegoats.rolgar2.util.structures.lists.LinkedList;
import org.thegoats.rolgar2.world.Position;
import org.thegoats.rolgar2.world.WorldCell;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Sur,
        Este,
        Oeste
    }

    private final Selection<TurnActionEnum> turnSelection;

    private final Selection<TurnActionEnum> freezedTurnSelection;

    private final Selection<MovementDirectionEnum> directionSelection;

    private Selection<CharacterData> stolenDeckSelection;

    private  Selection<CharacterData> setCardTargetSelection;

    private Selection<Card> cardSelection;

    private Selection<GameCharacter> gameCharacterSelection;
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

        cardSelection = new ConsoleSelection<Card>()
                .maxTries(3)
                .selectionPrompt("¿Que carta quiere usar?")
                .selectionRetryMessage("Opcion invalida.");

        setCardTargetSelection = null;
        stolenDeckSelection = null;

        gameCharacterSelection = null;
    }

    /**
     * realiza el turno, deriva a doFreezedTurn() si es necesario
     */
    @Override
    public void doTurn() {
        if(gameCharacter.getCharacterData().isDead()){
            logger.logDebug(gameCharacter.getCharacterData().getName() + " esta muerto");
            return;
        }
        int remainingMoves = gameCharacter.getCharacterData().getMoves();
        while (remainingMoves > 0 && gameCharacter.getGame().getAlivePlayersCount().size() > 1) {
            int layer = gameCharacter.getWorldCell().getPosition().getLayer();
            gameCharacter.getGame().worldViewer.showLayer(gameCharacter.getWorld(), layer);

            logger.logInfo("El jugador " + gameCharacter.getCharacterData().getName() + " realiza su turno.");

            var action = (gameCharacter.getCharacterData().isFreezed()
                    ? freezedTurnSelection.select()
                    : turnSelection.select())
                    .orElseThrow();

            boolean completed = false;
            try {
                // acá se ejecuta mover / usar carta / agarrar carta / etc.
                completed = doTurnAction(action);
            } catch (RuntimeException e) {
                // acá caen los Assert que fallan (sin cartas, sin carta en la celda, mazo lleno, etc.)
                logger.logWarning(e.getMessage());
            }

            if (completed) {
                remainingMoves--;
            } else {
                logger.logWarning("La accion no se ha completado.");
            }
        }
    }

    private boolean doTurnAction(TurnActionEnum action) {
        return switch (action) {
            case RealizarMovimiento -> move();
            case Atacar -> attack();
            case AgarrarCarta -> pickCard();
            case UsarCarta -> useCard();
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

    private boolean attack() {

        var neighborCells = gameCharacter.getWorldCell().getNeighborsOnSameLayer();


        var enemiesAround = neighborCells.stream()
                .map(WorldCell::getCharacter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if (enemiesAround.isEmpty()) {
            logger.logInfo("No hay enemigos alrededor para atacar.");
            return false;
        }

        GameCharacter target;

        //si hay un solo enemigo, lo atacamos directo
        if (enemiesAround.size() == 1) {
            target = enemiesAround.get(0);
            logger.logInfo("Atacando a " + target.getCharacterData().getName() + ".");
        } else {
            // si hay varios enemigos, mostramos menú para elegir
            var selection = new ConsoleSelection<GameCharacter>()
                    .addAllOptions(enemiesAround)
                    .maxTries(3)
                    .selectionPrompt("¿A qué enemigo quiere atacar?")
                    .selectionRetryMessage("Opcion invalida.");

            var chosen = selection.select();
            if (chosen.isEmpty()) {
                logger.logInfo("No se seleccionó enemigo para atacar.");
                return false;
            }

            target = chosen.get();
        }


        gameCharacter.attack(target);

        if (target.getCharacterData().getHealth() <= 0) {
            target.getWorldCell().setCharacter(null);
            logger.logInfo("El enemigo " + target.getCharacterData().getName() + " ha sido derrotado.");
        }

        return true;
    }



    private boolean useCard() {
        Assert.isTrue(!gameCharacter.getCharacterData().getDeck().isEmpty(), "El jugador tiene que tener cartas");
        updateCardSelection(gameCharacter.getCharacterData().getDeck(), "¿Que carta quiere usar?");

        return cardSelection.select()
                .map(card -> {
                    if (card instanceof CardWithCharacterTarget cardWithCharacterTarget) {
                        updateCardTargetSelection();
                        setCardTargetSelection.select().ifPresent(t -> cardWithCharacterTarget.setTarget(t));
                        gameCharacter.getGame().logger.logDebug(String.format("%s usa la carta %s sobre %s",
                                gameCharacter.getCharacterData().getName(),
                                cardWithCharacterTarget.getName(),
                                cardWithCharacterTarget.getTarget().getName()));
                    }

                    if (card instanceof StealingCard stealingCard) {
                        Assert.isTrue(!gameCharacter.getCharacterData().getDeck().isFull(), "El mazo está lleno");
                        try{
                            updateStolenDeckSelection();
                            stealingCard.setThiefDeck(gameCharacter.getCharacterData().getDeck());
                            stolenDeckSelection.select().ifPresent(character -> {
                                stealingCard.setStolenDeck(character.getDeck());
                                updateCardSelection(character.getDeck(), "¿Que carta quiere robar?");
                                cardSelection.select().ifPresent(card1 -> {
                                    stealingCard.setStolenCard(card1);
                                    gameCharacter.getGame().logger.logDebug(String.format("El jugador %s le roba %s a %s,",
                                            gameCharacter.getCharacterData().getName(),
                                            card1,
                                            character.getName()));
                                });
                            });
                        }catch (Exception e){
                            gameCharacter.getGame().logger.logWarning(e.getMessage());
                        }
                    }
                    // TODO: agregar lo que requiera teleport card

                    if (card instanceof TeleportCard teleportCard) {
                        try{
                            teleportCard.setCharacter(gameCharacter);
                            teleportCard.setOrigin(gameCharacter.getWorldCell());
                            gameCharacter.getWorldCell().getUpperNeighbor().ifPresent( t -> {
                                teleportCard.setDestination(t);
                            });
                            gameCharacter.getGame().logger.logWarning("se movio "+gameCharacter.getCharacterData().getName()+"al piso de arriba");
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                    card.use();
                    gameCharacter.getCharacterData().getDeck().remove(card);
                    return true;
                })
                .orElse(false);

    }

    private void updateCardSelection(CardDeck deck, String prompt) {
        Assert.notNull(deck, "deck no puede ser null");
        Assert.notNull(prompt, "prompt no puede ser null");
        cardSelection = new ConsoleSelection<Card>()
                .addAllOptions(deck.getCards())
                .maxTries(3)
                .selectionPrompt(prompt)
                .selectionRetryMessage("Opcion invalida.");
    }

    private void updateCardTargetSelection(){
        List<CharacterData> aliveCharacters = new LinkedList<>();
        for(CharacterData character: gameCharacter.getGame().getAllCharacterData()){
            if(character.isAlive()){
                aliveCharacters.add(character);
            }
        }
        setCardTargetSelection = new ConsoleSelection<CharacterData>()
                .addAllOptions(aliveCharacters)
                .maxTries(3)
                .selectionPrompt("¿A quién se la quiere aplicar?")
                .selectionRetryMessage("Opcion invalida.");
    }

    private void updateStolenDeckSelection(){
        List<CharacterData> validCharacters = new LinkedList<>();
        for(CharacterData character: gameCharacter.getGame().getAllCharacterData()){
            if(!character.equals(this.gameCharacter.getCharacterData()) &&
                    !character.getDeck().isEmpty() &&
                    character.isAlive()){
                validCharacters.add(character);
            }
        }
        Assert.positive(validCharacters.size(), "No hay ninguna carta disponible para robar");
        stolenDeckSelection = new ConsoleSelection<CharacterData>()
                .addAllOptions(validCharacters)
                .maxTries(3)
                .selectionPrompt("¿A quién le desea robar una carta??")
                .selectionRetryMessage("Opcion invalida.");
    }

    private Selection<Boolean> booleanSelection(String prompt, String retryMessage){
        Assert.notNull(prompt, "Prompt no debe ser null");
        Assert.notNull(retryMessage, "retryMessage no debe ser null");
        return new ConsoleSelection<Boolean>()
                .addOption("Si", true)
                .addOption("No", false)
                .maxTries(3)
                .selectionPrompt(prompt)
                .selectionRetryMessage(retryMessage);
    }

    private boolean gestionarAlianzas() {
        return false;
    }

    /**
     * Le pregunta al jugador si quiere recoger la carta que esta ubicada en la celda destino
     */
    public boolean pickCard() {
        CardDeck deck = gameCharacter.getCharacterData().getDeck();
        WorldCell cell = gameCharacter.getWorldCell();
        Assert.isTrue(cell.hasCard(), "La celda no contiene una carta");
        Assert.isTrue(!deck.isFull(), "El mazo está lleno");

        Selection<Boolean> election = booleanSelection("¿Desea tomar la carta "+cell.getCard().get()+"?", "Opcion invalida");
        if(!election.select().get()){
            return false;
        }
        deck.add(cell.getCard().get());
        gameCharacter.getWorldCell().setCard(null);
        return true;
    }

    /**
     * @return selector de gameCharacter cuyas opciones son los gameCharacters vivos
     */
    private Selection<GameCharacter> updateGameCharacterSelection(){
        List<GameCharacter> validCharacters =  new LinkedList<>();
        for(GameCharacter gameCharacter: gameCharacter.getGame().getGameCharacters()){
            if(gameCharacter.getCharacterData().isAlive()){
                validCharacters.add(gameCharacter);
            }
        }

        return new ConsoleSelection<GameCharacter>()
                .addAllOptions(validCharacters)
                .maxTries(3)
                .selectionPrompt("¿Qué jugador desea teletransportar?")
                .selectionRetryMessage("Opcion invalida.");
    }

    private Position northPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow() - 1,
                position.getColumn(),
                position.getLayer()
        );
    }

    private Position southPosition() {
        var position = gameCharacter.getWorldCell().getPosition();
        return clampedPosition(
                position.getRow() + 1,
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
