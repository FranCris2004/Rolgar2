package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.util.Assert;

public abstract class CardWithCharacterTarget implements Card {
    private CharacterData target = null;

    public void setTarget(CharacterData target) {
        Assert.notNull(target, "El target no debe ser nulo");
        this.target = target;
    }

    public CharacterData getTarget() {
        return target;
    }

    public void validateTarget() {
        Assert.notNull(target, "El target no ha sido setteado");
    }
}
