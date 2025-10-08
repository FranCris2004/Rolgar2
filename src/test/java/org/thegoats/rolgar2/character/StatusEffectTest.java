package org.thegoats.rolgar2.character;

import org.junit.jupiter.api.Test;
import org.thegoats.rolgar2.character.effects.ForceFieldEffect;

public class StatusEffectTest {
    @Test
    public void forceFieldTest() {
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
}
