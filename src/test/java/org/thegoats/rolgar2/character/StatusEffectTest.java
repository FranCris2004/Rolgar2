package org.thegoats.rolgar2.character;

import org.junit.jupiter.api.Test;
import org.thegoats.rolgar2.character.effects.HalfDamageEffect;
import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

public class StatusEffectTest {
    /**
     * Comprueba que ForceFieldEffect se aplique correctamente y dure lo que debe durar
     */
    @Test
    public void forceFieldTest() {
        System.out.println("\nforceFieldTest:");

        CharacterData character = new CharacterData("Juan", 100, 10, 10, 2, 1.0);

        // turno 1

        System.out.println(character);
        character.takeDamage(10); // deberia tomar 10 de daño
        assert character.getHealth() == 90;

        // turno 2

        System.out.println(character);
        character.updateEffects();
        character.applyEffect(new HalfDamageEffect(character, 1));
        character.takeDamage(10); // deberia tomar 5 de daño
        assert character.getHealth() == 85;

        // turno 3

        System.out.println(character);
        character.updateEffects(); // deberia remover el efecto
        character.takeDamage(10); // deberia tomar 10 de daño nuevamente
        assert character.getHealth() == 75;
    }

    /**
     * Comprueba que el efecto InvisibilityEffect se aplique correctamente y dure lo que debe durar
     */
    @Test
    public void invisibilityTest() {
        System.out.println("\ninvisibilityTest:");

        CharacterData character = new CharacterData("Juan", 100, 10, 10, 2, 1.0);

        System.out.println(character);
        character.applyEffect(new InvisibilityEffect(character, 3)); // dura tres turnos

        // pasaron 0 turnos

        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect); // deberia existir el efecto
        assert !character.isVisible(); // el personaje deberia ser invisible

        // paso 1 turno

        character.updateEffects();
        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect); // deberia existir el efecto
        assert !character.isVisible(); // el personaje deberia ser invisible

        // pasaron 2 turnos

        character.updateEffects();
        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect); // deberia existir el efecto
        assert !character.isVisible(); // el personaje deberia ser invisible

        // pasaron 3 turnos

        character.updateEffects();
        assert character.getEffects().stream().noneMatch(x -> x instanceof InvisibilityEffect); // no deberia existir el efecto
        assert character.isVisible(); // el personaje deberia volver a ser visible
    }
}
