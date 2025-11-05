package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

public abstract class CardWithCharacterTarget implements Card {
    private CharacterData target = null;

    /**
     * Dado un CharacterData, lo fija como el objetivo de la carta
     * @param target no null, objetivo de la carta
     */
    public void setTarget(CharacterData target) {
        Assert.notNull(target, "El target no debe ser nulo");
        this.target = target;
    }

    /**
     * @return el objetivo de la carta
     */
    public CharacterData getTarget() {
        return target;
    }

    /**
     * Chequea que el actual objetivo de la carta ya haya sido asignado a un CharacterData.
     */
    public void validateTarget() {
        Assert.notNull(target, "El target no ha sido setteado");
    }
}
