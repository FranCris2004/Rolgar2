package org.thegoats.rolgar2.player;

import java.util.Set;

public class PlayerAlliance {
    private final Set<Player> players = new org.thegoats.rolgar2.util.structures.sets.Set<>();
    private boolean inForce = true;

    public Set<Player> getPlayers() {
        return Set.copyOf(this.players);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean inForce() {
        return inForce;
    }

    public void broke() {
        this.inForce = false;
    }
}
