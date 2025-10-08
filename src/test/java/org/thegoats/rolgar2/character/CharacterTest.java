package org.thegoats.rolgar2.character;

import org.junit.jupiter.api.Test;
import org.thegoats.rolgar2.character.effects.ForceFieldEffect;

public class CharacterTest {
    @Test
    public void statusEffectTest() {
        CharacterData c0 = new CharacterData("Juan", 100, 10);

        c0.takeDamage(10); // deberia tomar 10 de daño
        assert c0.getHealth() == 90;

        c0.applyEffect(new ForceFieldEffect(1, 0.5f));
        c0.takeDamage(10); // deberia tomar 5 de daño
        assert c0.getHealth() == 85;

        c0.updateEffects(); // deberia remover el efecto
        c0.takeDamage(10); // deberia tomar 10 de daño nuevamente
        assert c0.getHealth() == 75;
    }
}
