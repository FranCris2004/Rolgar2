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
                .comparing(Player::getWinRate)
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PlayerRanking:\n");
        sb.append("Rank\t\tName\t\tGames Won Percentage\t\t(Games Won/Games Played)\n");
        int i = 1;
        for (Player player : players) {
            sb.append(String.format("%d.\t\t%s\t\t%f\t\t(%d/%d)\n",
                    i++,
                    player.getName(),
                    player.getWinRate(),
                    player.getGamesWon(),
                    player.getGamesPlayed()
            ));
        }

        return sb.toString();
    }
}
