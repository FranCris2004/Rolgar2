package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

public class Player {
    private String name;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public double getGamesWonPercentage() {
        return (gamesWon * 100.0) / gamesPlayed;
    }

    private void setName(String name) {
        Assert.validName(name,
                "El nombre sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'");
        this.name = name;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }

    @Override
    public String toString() {
        return name;
    }

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
