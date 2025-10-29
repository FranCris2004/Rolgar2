package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.effects.InvisibilityEffect;
import org.thegoats.rolgar2.util.Assert;

public class InvisibilityCard implements Card{
    private CharacterData target = null;

    /**
     * @param target dado un CharacterData o null, lo setea
     */
    public void setTarget(CharacterData target) {
        this.target = target;
    }

    /**
     * Aplica el efecto de la carta sobre el personaje destino, no puede ser nulo llegado este punto
     */
    @Override
    public void use() {
        Assert.notNull(target, "El target no ha sido setteado.");
        target.applyEffect(new InvisibilityEffect(target, 1));
    }
}
