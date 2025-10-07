package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;

import java.util.Objects;

public class CharacterStats {
    private int health;
    private int maxHealth;
    private int strength;

    public CharacterStats(int health, int maxHealth, int strength) {
        setMaxHealth(maxHealth);
        setHealth(health);
        setStrength(strength);
    }

    /**
     * @return La vida actual del personaje
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * @return La vida máxima del personaje
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }

    /**
     * @return El daño que inflige el personaje por cada golpe
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * @param health Debe ser mayor o igual a 0 y menor o igual a maxHealth
     */
    public void setHealth(int health) {
        Assert.inRange(health,0, maxHealth, "'health' debe ser mayor o igual a o y menor o igual a " + maxHealth);
        this.health = health;
    }

    /**
     * @param maxHealth Debe ser mayor a 0
     */
    public void setMaxHealth(int maxHealth) {
        Assert.nonNegative(maxHealth, "La vida máxima debe ser mayor a 0");
        this.maxHealth = maxHealth;
    }

    /**
     * @param strength Debe ser mayor o igual a 0
     */
    public void setStrength(int strength) {
        Assert.nonNegative(strength, "La fuerza debe ser mayor a 0");
        this.strength = strength;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass())
            return false;

        CharacterStats other = (CharacterStats) obj;
        return this == other
                || health == other.health
                && maxHealth == other.maxHealth
                && strength == other.strength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(health, maxHealth, strength);
    }

    @Override
    public String toString() {
        return String.format(
                "CharacterStats[health=%s,maxHealth=%s,strength=%s]",
                getHealth(), getMaxHealth(), getStrength()
        );
    }
}
