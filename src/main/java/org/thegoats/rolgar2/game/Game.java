package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.card.*;
import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.CharacterFactory;
import org.thegoats.rolgar2.game.config.GameConfig;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.util.structures.maps.SetMap;
import org.thegoats.rolgar2.world.World;
import org.thegoats.rolgar2.world.WorldViewer;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public final class Game {
    private int turnCount = 0;
    public final Logger logger;
    public final Random random;
    public final GameConfig config;
    public final WorldViewer worldViewer;

    private final World world;
    private final Set<Player> players;
    private final Set<GameCharacter> gameCharacters;
    private final Set<Card.Factory<? extends Card>> cardFactories;
    private final CharacterFactory playerCharacterFactory;
    private final CharacterFactory enemyCharacterFactory;


    /**
     * Construye una nueva partida del juego.
     * @param logger logger a utilizar para registrar mensajes
     * @param random generador de números aleatorios
     * @param players conjunto de jugadores que participarán
     * @param config configuración del juego (dificultad + mapa)
     */
    public Game(Logger logger, Random random, Set<Player> players, GameConfig config) {
        Assert.notNull(logger, "Logger");
        Assert.notNull(config, "random");
        Assert.notNull(players, "players");
        Assert.notNull(config, "config");

        this.logger = logger;
        this.random = random;
        this.config = config;

        this.players = new org.thegoats.rolgar2.util.structures.sets.Set<>();
        this.players.addAll(players);

        //
        // World
        //

        this.world = config.mapConfig().generateWorld();

        //
        // Bitmaps
        //

        // TODO: implementar algun tipo de configuracion para estos bitmaps (quizas como packs de texturas)
        Map<String, Bitmap> cardBitmapMap = new SetMap<>();

        var doubleMoveCardBitmap = new Bitmap(32, 32);
        var fireBallCardBitmap = new Bitmap(32, 32);
        var healingCardBitmap = new Bitmap(32, 32);
        var iceCardBitmap = new Bitmap(32, 32);
        var invisibilityCardBitmap = new Bitmap(32, 32);
        var shieldCardBitmap = new Bitmap(32, 32);
        var stealingCardBitmap = new Bitmap(32, 32);
        var teleportCardBitmap = new Bitmap(32, 32);

        doubleMoveCardBitmap.fill(Color.LIGHT_GRAY);
        fireBallCardBitmap.fill(Color.ORANGE);
        healingCardBitmap.fill(Color.GREEN);
        iceCardBitmap.fill(Color.CYAN);
        invisibilityCardBitmap.fill(Color.WHITE);
        shieldCardBitmap.fill(Color.BLUE);
        stealingCardBitmap.fill(Color.YELLOW);
        teleportCardBitmap.fill(Color.MAGENTA);

        cardBitmapMap.put(DoubleMoveCard.class.getName(), doubleMoveCardBitmap);
        cardBitmapMap.put(FireballCard.class.getName(), fireBallCardBitmap);
        cardBitmapMap.put(HealingCard.class.getName(), healingCardBitmap);
        cardBitmapMap.put(HealingCard.class.getName(), healingCardBitmap);
        cardBitmapMap.put(IceballCard.class.getName(), iceCardBitmap);
        cardBitmapMap.put(InvisibilityCard.class.getName(), invisibilityCardBitmap);
        cardBitmapMap.put(ShieldCard.class.getName(), shieldCardBitmap);
        cardBitmapMap.put(StealingCard.class.getName(), stealingCardBitmap);
        cardBitmapMap.put(TeleportCard.class.getName(), teleportCardBitmap);

        var characterBitmap = new Bitmap(32, 32);
        characterBitmap.fill(Color.WHITE);
        characterBitmap.drawLine(0, 0, characterBitmap.getWidth(), characterBitmap.getHeight(), Color.BLACK);
        characterBitmap.drawLine(0, characterBitmap.getHeight(), characterBitmap.getWidth(), 0, Color.BLACK);

        try {
            this.worldViewer = new WorldViewer(
                    config.mapConfig().getFloorBitmapMap(),
                    config.mapConfig().getWallBitmapMap(),
                    cardBitmapMap,
                    characterBitmap,
                    Color.BLACK,
                    new Color(0, 0, 0, 0),
                    new Color(0, 0, 0, 180),
                    100, 100
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //
        // GameCharacters
        //

        this.playerCharacterFactory = config.difficultyConfig().playerConfig().getCharacterFactory(random);
        this.enemyCharacterFactory = config.difficultyConfig().playerConfig().getCharacterFactory(random);
        this.gameCharacters = new org.thegoats.rolgar2.util.structures.sets.Set<>();
        {
            int i = 0;
            for (Player player : players) {
                this.gameCharacters.add(new GameCharacter(
                        this,
                        world,
                        player,
                        playerCharacterFactory.create(player.getName()),
                        world.getRandomEmptyCharacterWalkableCell(random),
                        GameCharacterPlayerTurnManager.class
                ));

                if (i++ % 2 == 1) { // crea un enemigo por cada dos jugadores
                    this.gameCharacters.add(new GameCharacter(
                            this,
                            world,
                            null,
                            enemyCharacterFactory.create("Enemigo" + i),
                            world.getRandomEmptyCharacterWalkableCell(random),
                            GameCharacterEnemyTurnManager.class
                    ));
                }
            }
        }

        //
        // Cards
        //

        this.cardFactories = config.difficultyConfig().cardConfig().getFactories(random);

        // TODO: configurar la cantidad de cartas que aparecen
        int cardCount = 10;
        for (int i = 0; i < cardCount; i++) {
            world.getRandomEmptyCharacterWalkableCell(random).setCard(
                    ((Card.Factory<? extends Card>) cardFactories.toArray()[random.nextInt(cardFactories.size())])
                            .create()
            );
        }

        logger.logDebug("Game constructor:");
        logger.logDebug("logger: " + logger);
        logger.logDebug("config: " + config);
        logger.logDebug("gameCharacters: " + gameCharacters);
    }

    /**
     * Inicia la ejecucion del juego, esto implica:
     * Cargar las configuraciones y mapas
     * Realizar las impresiones de inicio de juego
     * Iniciar el bucle de juego
     * Realizar las impresiones de fin del juego
     */
    public void run()
    {
        logger.logDebug("Game run:");

        while (hasNextTurn()) {
            nextTurn();
        }
        // TODO: decir quién ganó la partida
    }

    /**
     * Evalua si debe haber un proximo turno o si el juego debe terminar
     * @return trues si hay un proximo turno, false si no lo hay
     */
    private boolean hasNextTurn()
    {
        int aliveCharacters = 0;
        for(GameCharacter gameCharacter: gameCharacters){
            if(gameCharacter.getCharacterData().isAlive() && gameCharacter.isPlayerCharacter()){
                aliveCharacters++;
            }
        }
        // TODO: en el futuro el juego deberia terminar si los que quedan vivos son una alianza.
        // TODO: cuando queda solo un jugador, sigue el juego hasta que finalice la ronda. deberia terminar
        return aliveCharacters > 1;
    }

    /**
     * Todos los personajes hacen su turno
     */
    private void nextTurn()
    {
        logger.logDebug("Turn " + ++turnCount);
        for (GameCharacter gameCharacter : gameCharacters) {
                gameCharacter.getTurnManager().doTurn();
        }
    }

    /**
     *@return devuelve una lista con los CharacterData de todos los GameCharacter actuales
     */
    public List<CharacterData> getAllCharacterData(){
        List<CharacterData> characterDataList = new LinkedList<>();
        for(GameCharacter gameCharacter: gameCharacters){
            characterDataList.add(gameCharacter.getCharacterData());}
        return characterDataList;
    }

    public List<CharacterData> getAlivePlayersCount(){
        List<CharacterData> characterDataList = new LinkedList<>();
        for(GameCharacter gameCharacter: gameCharacters){
            if(gameCharacter.isPlayerCharacter() && gameCharacter.getCharacterData().isAlive()){
                characterDataList.add(gameCharacter.getCharacterData());
            }
        }
        return characterDataList;
    }
}
