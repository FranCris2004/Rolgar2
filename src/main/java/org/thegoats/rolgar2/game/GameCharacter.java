package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.player.Player;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;
import org.thegoats.rolgar2.world.World;
import org.thegoats.rolgar2.world.WorldCell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class GameCharacter {
    private final Game game;
    private final World world;
    private final Player player;
    private final CharacterData characterData;
    private final GameCharacterTurnManager turnManager;
    private WorldCell worldCell;

    public GameCharacter(
            Game game,
            World world,
            Player player,
            CharacterData characterData,
            WorldCell initialWorldCell,
            Class<? extends GameCharacterTurnManager> gameCharacterTurnManagerClass
    ) {
        Assert.notNull(game, "game no puede ser nulo");
        Assert.notNull(characterData, "characterData no puede ser nulo");
        Assert.notNull(player, "player no puede ser nulo");
        Assert.notNull(world, "world no puede ser nulo");
        setWorldCell(initialWorldCell);
        this.world = world;
        this.game = game;
        this.player = player;
        this.characterData = characterData;

        try {
            Constructor<?> ctor = gameCharacterTurnManagerClass.getDeclaredConstructor(GameCharacter.class);
            this.turnManager =  (GameCharacterTurnManager) ctor.newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cambia la celda en el mapa en la que se encuentra el personaje
     * @param worldCell no puede ser nulo
     */
    public void setWorldCell(WorldCell worldCell) {
        Assert.notNull(worldCell, "worldCell no puede ser nulo.");
        this.worldCell = worldCell;
    }


    public CharacterData getCharacterData(){
        return characterData;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame(){
        return game;
    }

    /**
     * @return mundo
     */
    public World getWorld() {
        return world;
    }

 
    /**
     * @return Devuelve la celda en el mapa en la que se encuentra el personaje
     */
    public WorldCell getWorldCell() {
        return worldCell;
    }

    /**
     * Determina si el personaje es un jugador
     * @return Devuelve True si el es un jugador, False en caso contrario
     */
    public boolean isPlayerCharacter() {
        return player != null;
    }

    /**
     * Realiza un ataque al personaje enviado por parametro, lo registra en el logger,
     * y se le aplica daño en funcion de la fuerza del atacante
     * @param character no puede ser nulo
     */
    public void attack(GameCharacter character) {
        Assert.notNull(character, "character no puede ser nulo");

        game.logger.logInfo(this + " ataca a " + character);
        character.characterData.takeDamage(this.characterData.getStrength());
    }

    /**
     * Coloca al personaje en la celda correspondiente a position
     * @param position no null, nueva posicion del personaje
     */
    public void moveCharacter(Position position){
        Assert.notNull(position, "la nueva position no puede ser null");

        var newCell = world.getCell(position);
        Assert.isTrue(newCell.characterCanMove(), "El personaje no puede moverse a la posicion: " + position);

        world.getCell(this.worldCell.getPosition()).setCharacter(null);
        try{
            newCell.setCharacter(this);
            setWorldCell(newCell);
        } catch (IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    // TODO: TELEPORT CHARACTER URGENTE !!!!!!!!!!

    @Override
    public String toString() {
        return characterData.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GameCharacter && Objects.equals(characterData, ((GameCharacter) o).characterData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterData);
    }

    /**
     * @return administrador de turnos del jugador
     */
    public GameCharacterTurnManager getTurnManager() {
        return turnManager;
    }
}
