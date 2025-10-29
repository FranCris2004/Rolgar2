package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

public class HealingCard implements Card {
    private int health;
    private CharacterData target = null;

    /**
     * crea la carta HealingCard
     * @param health vida a recibir
     */
    public HealingCard(int health){
        this.setHealth(health);
    }

    /**
     * setea el target
     * @param target characterData o null
     */
    public void setTarget(CharacterData target) {
        this.target = target;
    }

    /**
     * dado un health valido lo setea
     * @param health mayor a 0
     */
    private void setHealth(int health){
        Assert.positive(health,"health no puede ser menor a 0");
        this.health = health;
    }

    /**
     * utiliza la carta con el jugador target, target debe ser distinto de null
     * si al sumar la vida se supera la vida maxima de target, trunca en la vida maxima
     */
    @Override
    public void use() {
        Assert.notNull(target, "El target no ha sido setteado.");
        target.recoverHealth(this.health);
    }
}
