package org.thegoats.rolgar2.character.effects;

import org.thegoats.rolgar2.character.CharacterData;
import org.thegoats.rolgar2.character.StatusEffect;

/**
 * Efecto de doble movimiento: toma los movimientos del personaje y los duplica por 'duration' turnos
 */
public class DoubleMoveEffect extends StatusEffect {
    /**
     * Construye el efecto con un personaje destino y una duracion
     * @param character no null, personaje al cual aplicarle el efecto
     * @param duration mayor a cero, duracion del efecto en turnos
     */
    public DoubleMoveEffect(CharacterData character, int duration) {
        super(character, duration);
    }

    /**
     * Aplica el efecto sobre character (ver padre)
     */
    @Override
    public void onApply() {
        getCharacter().setMoves(getCharacter().getMoves()*2);
    }

    /**
     * Remueve el efecto de character (ver padre)
     */
    @Override
    public void onRemove() {
        getCharacter().setMoves(getCharacter().getMoves()/2);
    }
}
