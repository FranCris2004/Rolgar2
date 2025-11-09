package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleMoveEffect;

import java.util.Random;

/**
 * Carta de doble movimiento. Antes de ser usada se le debe settear un target y una duration. Duration debe ser extraida
 * del correspondiente config.json
 */
public class DoubleMoveCard extends CardWithStatusEffect {
    public DoubleMoveCard(int remainingTurns) {
        super(remainingTurns);
    }


    /**
     * Chequea que la carta ya tenga asignados target y duration, y de ser asi, le aplica al target el efecto de estado
     * DoubleMoveEffect
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().applyEffect(new DoubleMoveEffect(getTarget(), getRemainingTurns()));
    }

    /**
     * Devuelve la carta en formato string
     * @return formato en String de la carta, con el formato NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN
     */
    @Override
    public String toString(){
        return String.format("DoubleMoveCard[target=%s, Duration=%d]",
                getTarget().toString(),
                getRemainingTurns());
    }

    public static class Factory extends CardWithStatusEffect.Factory<DoubleMoveCard> {
        public Factory(Random random, int
                remainingTurnsFloor, int remainingTurnsRoof) {
            super(random, remainingTurnsFloor, remainingTurnsRoof);
        }

        @Override
        public DoubleMoveCard create() {
            return new DoubleMoveCard(getRandomRemainingTurns());
        }
    }
}
