package org.thegoats.rolgar2.entity;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

public abstract class Character extends Entity {
    //INTERFACES ----------------------------------------------------------------------------------------------
    //ENUMERADOS ----------------------------------------------------------------------------------------------
    //CONSTANTES ----------------------------------------------------------------------------------------------
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
    //ATRIBUTOS -----------------------------------------------------------------------------------------------
    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    //ATRIBUTOS TRANSITORIOS ----------------------------------------------------------------------------------
    //CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     *
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     * @param maxHealth Debe ser mayor a 0
     * @param health Debe ser mayor o igual a 0
     * @param strength Debe ser mayor o igual a 0
     * @param position No null
     */
    protected Character(String name, int maxHealth, int health, int strength, Position position) {
        super(position);
        setName(name);
        setMaxHealth(maxHealth); // Debe ser llamado antes que setHealth()
        setHealth(health);
        setStrength(strength);
    }
    //METODOS ABSTRACTOS --------------------------------------------------------------------------------------

    public abstract void doTurn();

    //METODOS HEREDADOS (CLASE)--------------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format(
                "Character[name=%s,health=%s,maxHealth=%s,strength=%s,position=%s]",
                name, health, maxHealth, strength, this.getPosition()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Character other = (Character) obj;
        return this.health == other.health &&
                this.maxHealth == other.maxHealth &&
                this.name.equals(other.name) &&
                this.strength == other.strength &&
                super.equals(other);
    }

    //METODOS HEREDADOS (INTERFACE)----------------------------------------------------------------------------
    //METODOS DE CLASE ----------------------------------------------------------------------------------------
    //METODOS GENERALES ---------------------------------------------------------------------------------------
    //METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    protected void attack(Character other){
        Assert.notNull(other, "'other' no debe ser null'");
        other.takeDamage(this.strength);
    }

    /**
     * Si el daño es mayor a la vide, la setea en 0, sino la setea en this.health - damage
     * @param damage Debe ser mayor a 0
     */
    public void takeDamage(int damage){
        Assert.nonNegative(damage, "el daño recibido debe ser mayor o igual a cero");

        if(damage > this.health){
            setHealth(0);
        } else {
            setHealth(getHealth() - damage);
        }
    }
    //METODOS DE CONSULTA DE ESTADO ---------------------------------------------------------------------------
    /**
     * @return True si el personaje esta vivo, False si esta muerto
     */
    public boolean isAlive(){
        return this.health > 0;
    }

    /**
     * @return True si esta muerto, False si esta vivo
     */
    public boolean isDead(){
        return !isAlive();
    }
    //GETTERS REDEFINIDOS -------------------------------------------------------------------------------------
    //GETTERS INICIALIZADOS -----------------------------------------------------------------------------------
    //GETTERS COMPLEJOS ---------------------------------------------------------------------------------------
    //GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * @return EL nombre del personaje
     */
    public String getName(){
        return this.name;
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

    //SETTERS COMPLEJOS----------------------------------------------------------------------------------------
    //SETTERS SIMPLES -----------------------------------------------------------------------------------------

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
    private void setHealth(int health) {
        Assert.inRange(health,0, maxHealth, "'health' debe ser mayor o igual a o y menor o igual a " + maxHealth);
        this.health = health;
    }

    /**
     * @param maxHealth Debe ser mayor a 0
     */
    private void setMaxHealth(int maxHealth) {
        Assert.nonNegative(maxHealth, "La vida máxima debe ser mayor a 0");
        this.maxHealth = maxHealth;
    }

    /**
     * @param strength Debe ser mayor o igual a 0
     */
    private void setStrength(int strength) {
        Assert.nonNegative(strength, "La fuerza debe ser mayor a 0");
        this.strength = strength;
    }
}
