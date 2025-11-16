package org.thegoats.rolgar2.player;
import org.thegoats.rolgar2.util.Assert;

import java.util.Set;


/**
 * Alianza de jugadores, implementada con un conjunto
 */
public class PlayerAlliance {
    private final Set<Player> players = new org.thegoats.rolgar2.util.structures.sets.Set<>();
    private boolean inForce = true;

    /**
     * @return conjunto de Players integrantes de la alianza
     */
    public Set<Player> getAllies() {
        return Set.copyOf(this.players);
    }

    /**
     * Dado un player, si es válido lo agrega a la alianza
     * @param player no null, fuera de la alianza jugador a agregar
     */
    public void addPlayer(Player player) {
        Assert.notNull(player, "El jugador a agregar no puede ser null");
        Assert.isTrue(!isAllied(player), "El jugador ya esta en la alianza");
        players.add(player);
    }

    /**
     * Dado un jugador, lo quita de la alianza
     * @param player no null, perteneciente a la alianza
     */
    public void removePlayer(Player player) {
        Assert.notNull(player, "El jugador a remover no puede ser null");
        Assert.isTrue(isAllied(player), "El jugador debe estar aliado para removerlo");
        players.remove(player);
    }

    /**
     * Dado un jugador, permite conocer si pertenece a la alianza
     * @param player no null, jugador del que se quiere conocer si pertenece a la alianza
     * @return true si esta aliado
     */
    public boolean isAllied(Player player) {
        Assert.notNull(player, "El jugador no puede ser null");
        return players.contains(player);
    }

    /**
     * @return true si la alianza sigue en pie
     */
    public boolean inForce() {
        return inForce;
    }

    /**
     * Disuelve la alianza
     */
    public void broke() {
        Assert.isTrue(inForce, "La alianza ya está disuelta");
        this.inForce = false;
    }
}
