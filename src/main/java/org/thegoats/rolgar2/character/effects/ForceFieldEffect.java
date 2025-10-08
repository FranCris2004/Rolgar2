package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

public class ForceFieldEffect extends StatusEffect {
    private final float incomingDamageFactorModifier;
    
    public ForceFieldEffect(int remainingTurns, float takeDamageFactorModifier) {
        super(remainingTurns);

        if (takeDamageFactorModifier < 0) {
            throw new IllegalArgumentException("takeDamageFactorModifier no debe ser negativo");
        }
        
        this.incomingDamageFactorModifier = takeDamageFactorModifier;
    }

    @Override
    public void onApply(CharacterData character) {
        character.setIncomingDamageFactor(incomingDamageFactorModifier);
    }

    @Override
    public void onRemove(CharacterData character) {
        character.setIncomingDamageFactor(incomingDamageFactorModifier);
    }

    @Override
    public String toString() {
        return String.format(
                "ForceFieldEffect[remainingTurns=%d, incomingDamageFactor=%.2f]",
                getRemainingTurns(),
                incomingDamageFactorModifier
        );
    }
}
