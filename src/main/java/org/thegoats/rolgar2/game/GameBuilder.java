package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.game.config.*;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;
import org.thegoats.rolgar2.util.Options;

import java.util.Set;

public class GameBuilder {
    private Logger logger;
    private Set<DifficultyConfig> difficulties;
    private Set<MapConfig> maps;
    private Set<GameCharacter> characters;

    public GameBuilder() {}

    public Game build() {
        return new Game(logger, new GameConfig(selectDifficulty(), selectMap()), characters);
    }

    public GameBuilder setLogger(Logger logger) {
        Assert.notNull(logger, "El logger no puede ser nulo.");
        this.logger = logger;
        return this;
    }

    public GameBuilder loadDifficulties(String difficultiesDirectoryPath) {
        difficulties = DifficultyLoader.loadDifficulties(difficultiesDirectoryPath);
        return this;
    }

    public GameBuilder loadMaps(String mapsDirectoryPath) {
        maps = MapLoader.loadMaps(mapsDirectoryPath);
        return this;
    }

    private DifficultyConfig selectDifficulty() {
        String difficultyName = new Options(
                "Seleccione la dificultad",
                (String[])difficulties.stream().map(DifficultyConfig::name).toArray(),
                "Dificultad invalida, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar una dificultad."));

        return difficulties.stream()
                .filter(difficultyConfig -> difficultyConfig.name().equals(difficultyName))
                .findFirst()
                .orElseThrow();
    }

    private MapConfig selectMap() {
        String mapName = new Options(
                "Seleccione el mapa",
                (String[])maps.stream().map(MapConfig::name).toArray(),
                "Mapa invalido, intentelo denuevo.",
                5,
                false)
                .choose()
                .orElseThrow(() -> new RuntimeException("El usuario no pudo seleccionar un mapa."));

        return maps.stream()
                .filter(difficultyConfig -> difficultyConfig.name().equals(mapName))
                .findFirst()
                .orElseThrow();
    }
}
