package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.game.config.CharacterConfig;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

// TODO: comentar

/**
 *  Fábrica de Character
 */
public class CharacterFactory {
    private final Random random;
    public final CharacterConfig config;

    public CharacterFactory(Random random, CharacterConfig config) {
        this.random = random;
        this.config = config;
    }

    /**
     *
     * @param name entre 3 y 20 caracteres alfanumericos, y '.-_'
     * @return CharacterData con stats ya predefinidos por el CharacterFactory y name recibido por parametro
     */
    public CharacterData create(String name)
    {
        return new CharacterData(
                name,
                random.nextInt(config.healthFloor(), config.healthRoof()),
                random.nextInt(config.strengthFloor(), config.strengthRoof()),
                config.inventorySize(),
                config.moves(),
                config.incomingDamageFactor()
        );
    }
}
