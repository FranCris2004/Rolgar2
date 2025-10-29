package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;
import org.thegoats.rolgar2.util.Assert;

/**
 * Efecto de congelamiento aplicado por la carta IceBall
 */

public class FreezeEffect extends StatusEffect {
    private int moves;
    /**
     * Construye el efecto de congelamiento con los turnos indicados
     * @param remainingTurns mayor a cero
     */
    public FreezeEffect(CharacterData character, int remainingTurns){
        super(character, remainingTurns);
    }

    /**
     * Guarda en un atributo auxiliar los movimientos por turno anteriores al congelamiento
     */
    @Override
    public void onApply() {
        this.moves = getCharacter().getMoves();
        getCharacter().setMoves(0);
    }

    /**
     * Devuelve sus movimientos por turno al personaje
     */
    @Override
    public void onRemove() {
        Assert.notNull(getCharacter(), "CharacterData no puede ser null");
        getCharacter().setMoves(moves);
    }
}
