package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

public class DoubleStrengthEffect extends StatusEffect {

    public DoubleStrengthEffect(int remainingTurns){
        super(remainingTurns);

    }

    @Override
    public void onApply(CharacterData character) {
        character.setStrength(character.getStrength()*2);
    }

    @Override
    public void onRemove(CharacterData character) {
        character.setStrength(character.getStrength()/2);
    }

    @Override
    public String toString() {
        return String.format(
                "DoubleStrengthEffect[remainingTurns=%d]",
                getRemainingTurns()
        );
    }

}
