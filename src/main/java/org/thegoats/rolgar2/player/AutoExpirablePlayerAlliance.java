package org.thegoats.rolgar2.player;

import org.thegoats.rolgar2.util.Assert;

/**
 * Alianza que expira luego de que transcurran 'duration' turnos
 */
public class AutoExpirablePlayerAlliance extends PlayerAlliance {
    private int remainingTurns;

    /**
     * Crea una alianza autoexpirable
     * @param duration mayor a cero, duracion que tendra la alianza
     */
    public AutoExpirablePlayerAlliance(int duration) {
        Assert.positive(duration, "duration debe ser mayor o igual a cero");
        this.remainingTurns = duration;
    }

    /**
     * Actualiza los turnos restantes: si es el último turno, se disuelve la alianza, sino, decrementa los turnos
     * restantes
     */
    public void updateRemainingTurns() {
        if (remainingTurns == 1) {
            broke();
        } else  {
            remainingTurns--;
        }
    }
}
