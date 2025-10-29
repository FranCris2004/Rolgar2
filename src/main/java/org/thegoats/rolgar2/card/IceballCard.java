package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.effects.FreezeEffect;
import org.thegoats.rolgar2.util.Assert;

public class IceballCard implements Card {
    CharacterData target = null;
    int effectDuration = 1;

    public void setEffectDuration(int duration) {
        Assert.positive(duration, "Los turnos no pueden ser negativos ni cero, se ingreso "+duration);
        effectDuration = duration;
    }

    public void setTarget(CharacterData target) {
        this.target = target;
    }

    @Override
    public void use() {
        if (target != null) {
            target.applyEffect(new FreezeEffect(target, effectDuration));
        }
    }
}
