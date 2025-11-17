package org.thegoats.rolgar2.game.config;

import org.thegoats.rolgar2.character.CharacterFactory;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record CharacterConfig(
        int healthFloor,
        int healthRoof,
        int strengthFloor,
        int strengthRoof,
        int inventorySize,
        int moves,
        double incomingDamageFactor
) {
    public CharacterConfig {
        Assert.positive(healthFloor, "healthFloor debe ser positivo");
        Assert.positive(healthRoof, "healthRoof debe ser positivo");
        Assert.isTrue(healthFloor < healthRoof, "healthFloor debe ser menor a healthRoof");
        Assert.positive(strengthFloor, "strengthFloor debe ser positivo");
        Assert.positive(strengthRoof, "strengthRoof debe ser positivo");
        Assert.isTrue(strengthFloor < strengthRoof, "strengthFloor debe ser menor a strengthRoof");
        Assert.nonNegative(inventorySize, "inventorySize debe ser mayor o igual a cero");
        Assert.nonNegative(moves, "moves debe mayor o igual a cero");
    }

    public CharacterFactory getCharacterFactory(Random random) {
        return new CharacterFactory(random, this);
    }
}
