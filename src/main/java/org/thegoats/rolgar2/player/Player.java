package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

/**
 * Jugador, representa a cada uno de los jugadores de la partida
 */
public class Player {
    private String name;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    /**
     * Construye el jugador si su nombre es válido
     * @param name no null, entre 3 y 20 caracteres alfanumericos, y '.-_'
     */
    public Player(String name) {
        setName(name);
    }
    /**
     * @return nombre del jugador
     */
    public String getName() {
        return name;
    }

    /**
     * @return cantidad de partidas jugadas de dicho jugador
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @return cantidad de partidas ganadas del jugador
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * @return porcentaje de victorias ganadas del jugador
     */
    public double getWinRate() {
        return (gamesWon * 100.0) / gamesPlayed;
    }

    /**
     * Valida y setea el nombre del jugador
     * @param name no null, entre 3 y 20 caracteres alfanuméricos, y '.-_'
     */
    private void setName(String name) {
        Assert.validName(name,
                "El nombre sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'");
        this.name = name;
    }

    /**
     * Incrementa una partida jugada
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Incrementa una partida ganada
     */
    public void incrementGamesWon() {
        this.gamesWon++;
    }

    /**
     * @return version en formato String del jugador
     */
    @Override
    public String toString() {
        return String.format("Player[name=%s, gamesPlayed=%d, gamesWon=%d, winRate=%.2f",
                name,
                gamesPlayed,
                gamesWon,
                getWinRate());
    }

    /**
     * Compara dos jugadores y determina si son iguales
     * @param o  el otro jugador con el que comparar
     * @return true si son la misma referencia o si tienen el mismo nombre
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        return name.equals(((Player) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
