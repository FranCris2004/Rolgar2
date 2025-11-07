package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

import java.util.*;

public class PlayerRanking {
    private final SortedSet<Player> players;

    /**
     * @param comparator criterio de orden personalizado por el que ordenar el ranking
     */
    public PlayerRanking(Comparator<Player> comparator) {
        Assert.notNull(comparator, "comparator no puede ser null");
        players = new TreeSet<>(comparator);
    }

    /**
     * Llama al constructor con un criterio que ordena primero por tasa de victorias, si hay empate luego por
     * partidas ganadas, y si hay empate luego por nombre.
     */
    public PlayerRanking() {
        this(Comparator
                .comparing(Player::getWinRate)
                .thenComparing(Player::getGamesWon)
                .thenComparing(Player::getName));
    }

    /**
     * @return Lista de jugadores que pertenecen al ranking
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * Dado un jugador, si es válido lo agrega al ranking
     * @param player no null, no en el ranking
     */
    public void addPlayer(Player player) {
        Assert.notNull(player, "player no puede ser nulo");
        Assert.isTrue(!players.contains(player), "player ya existe en el ranking");

        players.add(player);
    }

    /**
     * Dado un jugador, si es válido lo quita del ranking
     * @param player no null, en el ranking
     */
    public void removePlayer(Player player) {
        Assert.notNull(player, "El jugador a quitar del ranking no puede ser null");
        Assert.isTrue(players.contains(player), "El jugador no está en el ranking");
        players.remove(player);
    }

    /**
     * @return version en formato String de los rankings
     */
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
