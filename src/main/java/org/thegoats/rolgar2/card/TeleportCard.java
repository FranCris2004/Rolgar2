package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.game.GameCharacter;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.WorldCell;

public class TeleportCard implements Card {
    private WorldCell destination = null;
    private WorldCell origin = null;
    private GameCharacter character = null;
    // TODO: Implementar

    @Override
    public void use() {
        Assert.notNull(character, "character no puede ser null");
        Assert.notNull(origin, "origin no puede ser null");
        Assert.notNull(destination, "destination no puede ser null");
        Assert.isTrue(character.getCharacterData().isAlive(), "character debe estar vivo");
        Assert.isTrue(!destination.isOccupied(), "la celda destino tiene un muro o un personaje");
        Assert.isTrue(origin.hasCharacter(), "cell debe tener un personaje para teletransportar");
        destination.setCharacter(character);
        character.setWorldCell(destination);
        origin.setCharacter(null);
    }

    public static class Factory implements Card.Factory<TeleportCard> {
        @Override
        public TeleportCard create() {
            return new TeleportCard();
        }
    }

    /**
     * Setea el destino del teletransporte
     * @param cell no null, desocupada
     */
    public void setDestination(WorldCell cell){
        Assert.notNull(cell, "destination no puede ser null");
        Assert.isTrue(!cell.isOccupied(), "la celda destino tiene un muro o un personaje");
        this.destination = cell;
    }

    /**
     * Setea el punto de partida del teletransporte
     * @param cell no null, que contenga un personaje
     */
    public void setOrigin(WorldCell cell){
        Assert.notNull(cell, "cell no puede ser null");
        Assert.isTrue(cell.hasCharacter(), "cell debe tener un personaje para teletransportar");
        this.origin = cell;
    }

    /**
     * Setea el personaje a mover
     * @param character no null, vivo aun
     */
    public void setCharacter(GameCharacter character){
        Assert.notNull(character, "character no puede ser null");
        Assert.isTrue(character.getCharacterData().isAlive(), "character debe estar vivo");
        this.character = character;
    }

    /**
     * @return nombre de la carta
     */
    public String getName(){
        return "teletransporte";
    }

    /**
     *@return devuelve una version en formato String de la carta
     */
    @Override
    public String toString(){
        return String.format("TeleportCard[Origin=%s, Destination=%s, Character=%s",
                origin,
                destination,
                character);
    }
}
