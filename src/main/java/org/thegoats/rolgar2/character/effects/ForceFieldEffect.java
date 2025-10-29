package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Reduce el daño recibido
 */
public class ForceFieldEffect extends StatusEffect {
    private final float incomingDamageFactorModifier;
    
    public ForceFieldEffect(CharacterData character, int remainingTurns, float incomingDamageFactorModifier){
        super(character, remainingTurns);

        if (incomingDamageFactorModifier < 0) {
            throw new IllegalArgumentException("takeDamageFactorModifier no debe ser negativo");
        }
        
        this.incomingDamageFactorModifier = incomingDamageFactorModifier;
    }

    @Override
    public void onApply(CharacterData character) {
        character.setIncomingDamageFactor(character.getIncomingDamageFactor() - incomingDamageFactorModifier);
    }

    @Override
    public void onRemove(CharacterData character) {
        character.setIncomingDamageFactor(character.getIncomingDamageFactor() + incomingDamageFactorModifier);
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
