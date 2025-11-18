package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.util.Assert;

/**
 * Representa la configuración completa de una partida del juego.
 * @param difficultyConfig La configuración de dificultad (estadísticas de jugador, enemigo, cartas, etc.)
 * @param mapConfig La configuración del mapa (pisos, paredes y estructura del mundo)
 */
public record GameConfig(
        DifficultyConfig difficultyConfig,
        MapConfig mapConfig
) {
    /**
     * Constructor del record.
     *  Además de asignar los campos, valida que no sean null los campos, si lo es alguno lanza excepcion
     */
    public GameConfig {
        Assert.notNull(difficultyConfig, "difficultyConfig no puede ser nulo");
        Assert.notNull(mapConfig, "mapConfig no puede ser nulo");
    }
}
