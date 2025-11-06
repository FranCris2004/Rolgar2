package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

/**
 * Muchas de las cartas que tienen un CharacterData como target, le aplican un efecto de estado StatusEffect a dicho personaje,
 * por lo tanto abstraemos en esta clase el atributo remainingTurns, que sera la duracion del efecto de estado en turnos,
 * y los metodos setRemainingTurns, getRemainingTurns, y validateRemainingTurns. Todas las cartas que apliquen un efecto
 * de estado heredaran de esta clase.
 */
public abstract class CardWithStatusEffect extends CardWithCharacterTarget {
    private Integer duration = null;

    /**
     * Recibe una cantidad de turnos restantes 'remainingTurns', y si son mayores a 0 los setea.
     * @param duration mayor a cero, turnos que durara el efecto de estado de la carta al ser aplicado sobre un personaje
     */
    public void setDuration(int duration) {
        Assert.positive(duration, "remainingTurns debe ser positivo.");
        this.duration = duration;
    }

    /**
     * Devuelve los turnos que la carta durara al usarse sobre un personaje
     * @return turnos que durara la carta
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Chequea que los turnos ya hayan sido correctamente setteados y no sigan siendo null.
     */
    public void validateDuration() {
        Assert.notNull(duration, "remainingTurns no ha sido setteado.");
    }
}
