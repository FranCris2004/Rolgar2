package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.util.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterData {
    //
    // Constantes estaticas
    //

    public static final float MIN_INCOMING_DAMAGE_FACTOR = 0.0f;
    public static final float MAX_INCOMING_DAMAGE_FACTOR = 2.0f;

    //
    // Atributos privados
    //

    private String name;
    private int health;
    private int maxHealth;
    private int strength;
    private boolean visible = true;
    private boolean isFreezed = false;
    private double incomingDamageFactor;
    private int moves;
    private Card[] inventory;

    /**
     * Lista de efectos (buffs y debuffs) activos en el personaje
     */
    private List<StatusEffect> effects = new LinkedList<>();

    //
    // Constructor
    //

    /**
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     * @param maxHealth Debe ser mayor a 0
     * @param strength Debe ser mayor o 0
     */
    public CharacterData(String name, int maxHealth, int strength, int inventorySize, int moves, double incomingDamageFactor) {
        setName(name);
        setMaxHealth(maxHealth);
        setHealth(maxHealth); // la vida inicial es la vida maxima
        setStrength(strength);
        setMoves(moves);
        setIncomingDamageFactor(incomingDamageFactor);
        initInventory(inventorySize);

    }

    //
    // Comportamiento
    //

    /**
     * Actualiza la lista de efectos activos
     */
    public void updateEffects() {
        // deja en la lista solo los efectos que no expiraron
        effects = effects.stream().filter((effect) -> {
            effect.tick();

            if (!effect.isExpired())
                return true;

            effect.onRemove(this);
            return false;
        }).collect(Collectors.toList());
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
        setHealth(Math.max(getHealth() - (int)(damage * incomingDamageFactor), 0));
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

    /**
     * @return true si esta congelado
     */
    public boolean isFreezed() {
        return isFreezed;
    }

    /**
     * @return Factor por el cual se multiplica el daño entrante, esta entre MIN_INCOMING_DAMAGE_FACTOR y MAX_INCOMING_DAMAGE_FACTOR
     */
    public float getIncomingDamageFactor() {
        return incomingDamageFactor;
    }

    /**
     * @return Una copia de la lista de efectos activos en el personaje
     */
    public List<StatusEffect> getEffects() {
        return List.copyOf(effects);
    }

    /**
     * @return una copia del inventario
     */
    public Card[] getInventory() {
        return inventory.clone();
    }

    /**
     * @return movimientos por turno del personaje
     */
    public int getMoves() {
        return moves;
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
        Assert.positive(maxHealth, "La vida máxima debe ser mayor a 0");
        this.maxHealth = maxHealth;
    }

    /**
     * @param strength Debe ser mayor o igual a 0
     */
    public void setStrength(int strength) {
        Assert.positive(strength, "La fuerza debe ser mayor a 0");
        this.strength = strength;
    }

    /**
     * @param visible true si el personaje es visible, false si es invisible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @param incomingDamageFactor cualquier float, se recorta entre MIN_INCOMING_DAMAGE_FACTOR y MAX_INCOMING_DAMAGE_FACTOR
     */
    public void setIncomingDamageFactor(double incomingDamageFactor) {
        // mantiene takeDamageFactor entre MAX_TAKE_DAMAGE_FACTOR y MIN_TAKE_DAMAGE_FACTOR
        this.incomingDamageFactor = Math.clamp(incomingDamageFactor, MIN_INCOMING_DAMAGE_FACTOR, MAX_INCOMING_DAMAGE_FACTOR);
    }

    /**
     * Dado unos movimientos por turno dados, si son validos los setea. Se permite 0 por posibles usos de cartas.
     * @param moves movimientos por turno del personaje
     */
    public void setMoves(int moves) {
        Assert.nonNegative(moves, "Los movimientos no pueden ser negatovos. Se ingreso"+moves);
        this.moves = moves;
    }

    /**
     * Inicializa todas las cartas del inventario en null
     * @param inventorySize mayor a cero, tamanio del inventario
     */
    private void initInventory(int inventorySize){
        Assert.positive(inventorySize, "El tamanio del inventario deberia ser positivo. Se ingreso "+inventorySize);
        this.inventory = new Card[inventorySize];
        for (int i = 0; i < inventorySize; i++) {
            inventory[i] = null;
        }
    }

    /**
     * Aplica un efecto al personaje y lo agrega a la lista de efectos activos
     * @param effect efecto a aplicar, no nulo, si el mismo objeto se paso dos veces, se ignora
     */
    public void applyEffect(StatusEffect effect) {
        Assert.notNull(effect, "El efecto no puede ser nulo");

        // si la referencia al efecto ya existe en la lista, no lo vuelve a agregar
        if (effects.stream().anyMatch((incomingEffect) -> effect == incomingEffect)) {
            return; // en vez de lanzar excepcion, lo ignora
        }

        effects.add(effect);
        effect.onApply(this);
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
        return String.format("CharacterData[name=%s, health=%s, maxHealth=%s, strength=%s, visible=%s, incomingDamageFactor=%s, effects=%s]",
                name, health, maxHealth, strength, visible, incomingDamageFactor, effects
        );
    }

    @Override
    public boolean equals(Object obj) {
        // si dos personajes comparten nombre y stats, siguen siendo personajes distintos, por lo tanto, solo su referencia los diferencia
        return this == obj;
    }
}
