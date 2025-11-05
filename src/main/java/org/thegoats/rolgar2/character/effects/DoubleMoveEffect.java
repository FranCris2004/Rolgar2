package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

public class DoubleMoveEffect extends StatusEffect {
    public DoubleMoveEffect(CharacterData character, int remainingTurns) {
        super(character, remainingTurns);
    }

    @Override
    public void onApply() {
        getCharacter().setMoves(getCharacter().getMoves()*2);
    }

    @Override
    public void onRemove() {
        getCharacter().setMoves(getCharacter().getMoves()/2);
    }
}
