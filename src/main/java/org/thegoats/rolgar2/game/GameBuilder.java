package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.Logger;

import java.util.Set;

public class GameBuilder {
    private Logger logger;
    private Set<DifficultyData> difficulties;
    private Set<MapData> maps;
    private Set<GameCharacter> characters;

    public GameBuilder() {}

    public Game build() {
        return new Game(logger, selectDifficulty(), selectMap(), characters);
    }

    public GameBuilder setLogger(Logger logger) {
        Assert.notNull(logger, "El logger no puede ser nulo.");
        this.logger = logger;
        return this;
    }

    public GameBuilder loadDifficulties(String difficultiesDirectoryPath) {
        return this;
    }

    public GameBuilder loadMaps(String mapsDirectoryPath) {
        return this;
    }

    private DifficultyData selectDifficulty() {
        return null;
    }

    private MapData selectMap() {
        return null;
    }
}
