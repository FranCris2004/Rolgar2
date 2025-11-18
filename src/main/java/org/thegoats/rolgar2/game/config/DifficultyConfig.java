package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.game.config.card.CardConfigBundle;
import org.thegoats.rolgar2.util.Assert;

/**
 * Representa la configuración completa de una dificultad del juego.
 * @param name nombre que la identifica (por ejemplo: "easy", "hard")
 * @param playerConfig La configuración del personaje jugador
 * @param enemyConfig La configuración del personaje enemigo
 * @param cardConfig El conjunto de configuraciones de cartas asociadas a esa dificultad
 */
public record DifficultyConfig(
        String name,
        CharacterConfig playerConfig,
        CharacterConfig enemyConfig,
        CardConfigBundle cardConfig
) {
    /**
     * Constructor del record. Valida que los campos no sean nulos si no lanza excepcion
     *
     */
    public DifficultyConfig {
        Assert.notNull(name, "name");
        Assert.notNull(playerConfig, "playerConfig");
        Assert.notNull(enemyConfig, "enemyConfig");
        Assert.notNull(cardConfig, "cardConfig");
    }

    /**
     * Se sobrescribe toString para que, al imprimir un DifficultyConfig,
     * se muestre directamente su nombre
     * @return Devuelve el nombre de la dificultad
     */
    @Override
    public String toString() {
        return name;
    }
}
