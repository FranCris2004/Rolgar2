package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Reduce el daño recibido
 */
public class HalfDamageEffect extends StatusEffect {
    public HalfDamageEffect(CharacterData character, int remainingTurns){
        super(character, remainingTurns);
    }

    @Override
    public void onApply() {
        getCharacter().setIncomingDamageFactor(getCharacter().getIncomingDamageFactor()/2);
    }

    @Override
    public void onRemove() {
        getCharacter().setIncomingDamageFactor(getCharacter().getIncomingDamageFactor()*2);
    }

    @Override
    public String toString() {
        return String.format(
                "ForceFieldEffect[remainingTurns=%d, incomingDamageFactor=%.2f]",
                getRemainingTurns()
        );
    }
}
