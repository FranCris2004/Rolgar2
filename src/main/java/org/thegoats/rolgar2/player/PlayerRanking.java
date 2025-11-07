package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

import java.util.*;

public class PlayerRanking {
    private final SortedSet<Player> players;

    public PlayerRanking(Comparator<Player> comparator) {
        players = new TreeSet<>(comparator);
    }

    public PlayerRanking() {
        this(Comparator
                .comparing(Player::getGamesWonPercentage)
                .thenComparing(Player::getGamesWon)
                .thenComparing(Player::getName));
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public void addPlayer(Player player) {
        Assert.notNull(player, "player no puede ser nulo");
        Assert.isTrue(!players.contains(player), "players ya existe en el ranking");

        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
