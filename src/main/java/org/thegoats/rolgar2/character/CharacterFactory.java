package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.game.config.CharacterConfig;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 *  Fábrica de Character
 */
public class CharacterFactory {
    private final Random random;
    public final CharacterConfig config;

    /**
     *
     * @param random no null, generador aleatorio de valores
     * @param config no null, configuracion de Character
     */
    public CharacterFactory(Random random, CharacterConfig config) {
        Assert.notNull(random, "Random no debe ser null");
        Assert.notNull(config, "Config no debe ser null");
        this.random = random;
        this.config = config;
    }

    /**
     *
     * @param name entre 3 y 20 caracteres alfanumericos, y '.-_'
     * @return CharacterData con stats predefinidos en config, y otros con valores aleatorios de floor y roof definidos
     * en config. Name no null recibido por parametro
     */
    public CharacterData create(String name)
    {
        Assert.validName(name, "Name debe tener entre 3 y 20 caracteres alfanumericos, incluyendo ' .-_ ' ");
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
