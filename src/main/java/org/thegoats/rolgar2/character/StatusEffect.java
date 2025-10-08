package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;

public abstract class StatusEffect {
    /**
     * Turnos que le quedan al efecto para expirar
     */
    private int remainingTurns;

    public StatusEffect(int remainingTurns) {
        Assert.positive(remainingTurns, "El efecto debe durar al menos un turno");
        this.remainingTurns = remainingTurns;
    }

    /**
     * Reduce el contador de turnos que le quedan al efecto para expirar
     */
    void tick() {
        remainingTurns--;
    };

    /**
     * @return true si el efecto expiro, false si sigue vigente
     */
    boolean isExpired() {
        return remainingTurns <= 0;
    }

    /**
     * Se aplica el efecto al personaje
     * @param character personaje objetivo
     */
    public abstract void onApply(CharacterData character);

    /**
     * Se remueve el efecto del personaje
     * @param character personaje objetivo
     */
    public abstract void onRemove(CharacterData character);
}
