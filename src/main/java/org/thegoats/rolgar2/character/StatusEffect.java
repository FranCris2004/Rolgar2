package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;

public abstract class StatusEffect {
    /**
     * Turnos que le quedan al efecto para expirar
     */
    private int remainingTurns;
    private CharacterData character = null;

    public StatusEffect(CharacterData character, int remainingTurns) {
        setCharacter(character);
        setRemainingTurns(remainingTurns);
    }

    /**
     * Turnos que le quedan al efecto para expirar
     */
    public int getRemainingTurns() {
        return remainingTurns;
    }

    /**
     * @return personaje al que se le aplicó el efecto de estado
     */
    protected CharacterData getCharacter() {
        return character;
    }

    /**
     * @param character no null, personaje al que se le aplica el efecto
     */
    private void setCharacter(CharacterData character) {
        Assert.notNull(character, "character no puede ser null");
        this.character = character;
    }

    /**
     * @param remainingTurns mayor a cero
     */
    private void setRemainingTurns(int remainingTurns) {
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
     */
    public abstract void onApply();

    /**
     * Se remueve el efecto del personaje
     */
    public abstract void onRemove();
}
