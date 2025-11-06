package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.character.effects.DoubleMoveEffect;

/**
 * Carta de doble movimiento. Antes de ser usada se le debe settear un target y una duration. Duration debe ser extraida
 * del correspondiente config.json
 */
public class DoubleMoveCard extends CardWithStatusEffect {

    /**
     * Chequea que la carta ya tenga asignados target y duration, y de ser asi, le aplica al target el efecto de estado
     * DoubleMoveEffect
     */
    @Override
    public void use() {
        validateTarget();
        validateDuration();
        getTarget().applyEffect(new DoubleMoveEffect(getTarget(), getDuration()));
    }

    /**
     * Devuelve la carta en formato string
     * @return formato en String de la carta, con el formato NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN
     */
    @Override
    public String toString(){
        return String.format("DoubleMoveCard[target=%s, Duration=%d]", getTarget().toString(), getDuration());
    }
}
