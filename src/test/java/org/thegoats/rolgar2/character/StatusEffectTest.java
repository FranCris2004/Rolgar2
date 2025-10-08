package org.thegoats.rolgar2.character;

import org.junit.jupiter.api.Test;
import org.thegoats.rolgar2.character.effects.ForceFieldEffect;
import org.thegoats.rolgar2.character.effects.InvisibilityEffect;

public class StatusEffectTest {
    /**
     * Comprueba que ForceFieldEffect se aplique correctamente y dure lo que debe durar
     */
    @Test
    public void forceFieldTest() {
        System.out.println("\nforceFieldTest:");

        CharacterData character = new CharacterData("Juan", 100, 10);

        System.out.println(character);
        character.takeDamage(10); // deberia tomar 10 de daño
        assert character.getHealth() == 90;

        System.out.println(character);
        character.applyEffect(new ForceFieldEffect(1, 0.5f));
        character.takeDamage(10); // deberia tomar 5 de daño
        assert character.getHealth() == 85;

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

        CharacterData character = new CharacterData("Juan", 100, 10);

        System.out.println(character);
        character.applyEffect(new InvisibilityEffect(3)); // dura tres turnos

        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect); // deberia existir el efecto
        assert !character.isVisible(); // el personaje deberia ser invisible

        character.updateEffects();
        // deberia existir el efecto
        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect);
        assert !character.isVisible(); // el personaje deberia ser invisible

        character.updateEffects();
        // deberia existir el efecto
        assert character.getEffects().stream().anyMatch(x -> x instanceof InvisibilityEffect);
        assert !character.isVisible(); // el personaje deberia ser invisible

        character.updateEffects();
        // no deberia existir el efecto
        assert character.getEffects().stream().noneMatch(x -> x instanceof InvisibilityEffect);
        assert character.isVisible(); // el personaje deberia volver a ser visible
    }
}
