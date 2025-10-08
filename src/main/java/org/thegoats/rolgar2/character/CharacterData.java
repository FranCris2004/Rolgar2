package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

public class CharacterData {
    //
    // Atributos privados
    //

    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private boolean visible;

    //
    // Constructor
    //

    /**
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     * @param maxHealth Debe ser mayor a 0
     * @param health Debe ser mayor o igual a 0
     * @param strength Debe ser mayor o igual a 0
     * @param position No null
     */
    public CharacterData(String name, int maxHealth, int health, int strength, Position position) {
        setName(name);
        setMaxHealth(maxHealth);
        setHealth(health);
        setStrength(strength);
    }

    //
    // Setters compuestos
    //

    /**
     * Reduce la vida y la trunca en 0
     * @param damage Debe ser mayor a 0
     */
    public void takeDamage(int damage){
        Assert.nonNegative(damage, "el daño recibido no puede ser negativo");
        setHealth(Math.max(getHealth() - damage, 0));
    }

    /**
     * Aumenta la vida y la trunca a maxHealth
     * @param health puntos de vida a recuperar
     */
    public void recoverHealth(int health){
        Assert.nonNegative(health, "los puntos de vida a recuperar no pueden ser negativos");
        setHealth(Math.min(getHealth() + health, maxHealth));
    }

    //
    // Getters
    //

    /**
     * @return EL nombre del personaje
     */
    public String getName(){
        return name;
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
     * @return true si el personaje es visible, false si es invisible
     */
    public boolean isVisible() {
        return visible;
    }

    //
    // Setters
    //

    /**
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     */
    public void setName(String name) {
        Assert.notNull(name, "El nombre no puede ser nulo");
        Assert.validName(name, "El nombre sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'");
        this.name = name;
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

    /**
     * @param visible true si el personaje es visible, false si es invisible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //
    // Estado
    //

    /**
     * @return True si el personaje esta vivo, False si murio
     */
    public boolean isAlive(){
        return getHealth() > 0;
    }

    /**
     * @return True si el personaje murio, False si esta vivo
     */
    public boolean isDead(){
        return !isAlive();
    }

    //
    // Implemetacion de metodos abstractos
    //

    @Override
    public String toString() {
        return String.format(
                "{" +
                        "\"name\":\"%s\"," +
                        "\"health\":%d," +
                        "\"maxHealth\":%d," +
                        "\"strength\":%d" +
                "}",
                name, health, maxHealth, strength
        );
    }

    @Override
    public boolean equals(Object obj) {
        // si dos personajes comparten nombre y stats, siguen siendo personajes distintos
        // por lo tanto solo su referencia los diferencia
        return this == obj;
    }
}
