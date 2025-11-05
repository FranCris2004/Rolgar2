package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

/**
 * Muchas de las cartas que tienen un CharacterData como target, le aplican un efecto de estado StatusEffect a dicho personaje,
 * por lo tanto abstraemos en esta clase el atributo remainingTurns, que sera la duracion del efecto de estado en turnos,
 * y los metodos setRemainingTurns, getRemainingTurns, y validateRemainingTurns. Todas las cartas que apliquen un efecto
 * de estado heredaran de esta clase.
 */
public abstract class CardWithStatusEffect extends CardWithCharacterTarget {
    private Integer remainingTurns = null;

    /**
     * Recibe una cantidad de turnos restantes 'remainingTurns', y si son mayores a 0 los setea.
     * @param remainingTurns mayor a cero, turnos que durara el efecto de estado de la carta al ser aplicado sobre un personaje
     */
    public void setRemainingTurns(int remainingTurns) {
        Assert.positive(remainingTurns, "remainingTurns debe ser positivo.");
        this.remainingTurns = remainingTurns;
    }

    /**
     * Devuelve los turnos que la carta durara al usarse sobre un personaje
     * @return turnos que durara la carta
     */
    public int getRemainingTurns() {
        return remainingTurns;
    }

    /**
     * Chequea que los turnos ya hayan sido correctamente setteados y no sigan siendo null.
     */
    public void validateRemainingTurns() {
        Assert.notNull(remainingTurns, "remainingTurns no ha sido setteado.");
    }
}
