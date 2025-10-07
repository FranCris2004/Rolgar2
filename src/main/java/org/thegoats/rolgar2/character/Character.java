package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.world.Position;

public final class Character {
    private String name;
    private final CharacterStats stats;

    /**
     *
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     * @param maxHealth Debe ser mayor a 0
     * @param health Debe ser mayor o igual a 0
     * @param strength Debe ser mayor o igual a 0
     */
    public Character(String name, int maxHealth, int health, int strength) {
        setName(name);
        stats = new CharacterStats(health, maxHealth, strength);
    }

    @Override
    public String toString() {
        return String.format(
                "Character[name=%s,stats=%s]",
                name, stats
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Character other = (Character) obj;
        return this == other
                || this.name.equals(other.name)
                && this.stats.equals(other.stats);
    }

    /**
     * Reduce la vida y la trunca en 0
     * @param damage Debe ser mayor a 0
     */
    public void takeDamage(int damage){
        Assert.nonNegative(damage, "el daño recibido debe ser mayor o igual a cero");
        stats.setHealth(Math.max(stats.getHealth() - damage, 0));
    }

    /**
     * @return True si el personaje esta vivo, False si esta muerto
     */
    public boolean isAlive(){
        return stats.getHealth() > 0;
    }

    /**
     * @return True si esta muerto, False si esta vivo
     */
    public boolean isDead(){
        return !isAlive();
    }

    /**
     * @return EL nombre del personaje
     */
    public String getName(){
        return name;
    }

    /**
     * @param name no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     */
    public void setName(String name) {
        Assert.notNull(name, "El nombre no puede ser nulo");
        Assert.validName(name, "El nombre sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'");
        this.name = name;
    }

    /**
     * @return referencia a las stats del personaje
     */
    public CharacterStats getStats() {
        return stats;
    }
}
