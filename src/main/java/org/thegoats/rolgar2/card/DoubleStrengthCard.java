package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.effects.DoubleStrengthEffect;
import org.thegoats.rolgar2.util.Assert;

public class DoubleStrengthCard implements Card{
    private CharacterData target = null;

    /**
     *
     * @param target objetivo a setear
     */
    public void setTarget(CharacterData target) {
        this.target = target;
    }

    @Override
    public void use() {
        Assert.notNull(target, "El target no ha sido setteado.");
        target.applyEffect(new DoubleStrengthEffect(1));
    }
}

