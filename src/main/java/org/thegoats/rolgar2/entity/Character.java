package org.thegoats.rolgar2.entity;

import org.thegoats.rolgar2.world.Position;

public class Character extends Entity {
    //INTERFACES ----------------------------------------------------------------------------------------------
    //ENUMERADOS ----------------------------------------------------------------------------------------------
    //CONSTANTES ----------------------------------------------------------------------------------------------
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
    private String name;
    private int health, maxHealth;
    private int strength;


    //ATRIBUTOS -----------------------------------------------------------------------------------------------
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
    //METODOS HEREDADOS (CLASE)--------------------------------------------------------------------------------
    //METODOS HEREDADOS (INTERFACE)----------------------------------------------------------------------------
    //METODOS DE CLASE ----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return this.name + " Vida: " + this.health + "/" + this.maxHealth + " Daño: " + this.strength +
                " Posicion: " +  this.position.toString();
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
                this.position.equals(other.position);
    }

    //METODOS GENERALES ---------------------------------------------------------------------------------------
    //METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Si el daño es mayor a la vide, la setea en 0, sino la setea en this.health - damage
     * @param damage Debe ser mayor a 0
     */
    public void takeDamage(int damage){
        if(damage <= 0){
            throw new  IllegalArgumentException("El daño a recibir debe ser mayor a cero");
        }
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
        if(name == null){
            throw new NullPointerException("El nombre no puede ser nulo");
        }
        if(name.matches("^[a-zA-Z0-9._-]{3,20}$")){
            throw new IllegalArgumentException("El nombre sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'");
        }
        this.name = name;
    }

    /**
     * @param health Debe ser mayor o igual a 0
     */
    private void setHealth(int health) {
        if(health < 0){
            throw new IllegalArgumentException("La vida debe ser mayor o igual a 0");
        }
        this.health = health;
    }

    /**
     * @param maxHealth Debe ser mayor a 0
     */
    private void setMaxHealth(int maxHealth) {
        if(maxHealth <= 0){
            throw new IllegalArgumentException("La vida máxima debe ser mayor a 0");
        }
        this.maxHealth = maxHealth;
    }

    /**
     * @param strength Debe ser mayor o igual a 0
     */
    private void setStrength(int strength) {
        if(strength < 0){
            throw new IllegalArgumentException("La fuerza debe ser mayor o igual a 0");
        }
        this.strength = strength;
    }


}