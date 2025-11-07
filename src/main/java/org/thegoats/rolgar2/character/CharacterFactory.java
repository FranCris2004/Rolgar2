package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

// TODO: comentar

public class CharacterFactory {
    private final Random random;
    private final int healthFloor;
    private final int healthRoof;
    private final int strengthFloor;
    private final int strengthRoof;
    private final int inventorySize;
    private final int moves;
    private final double incomingDamageFactor;

    public CharacterFactory(Random random,
                            int healthFloor,
                            int healthRoof,
                            int strengthFloor,
                            int strengthRoof,
                            int inventorySize,
                            int moves,
                            double incomingDamageFactor) {
        Assert.positive(healthFloor, "healthFloor debe ser positivo");
        Assert.positive(healthRoof, "healthRoof debe ser positivo");
        Assert.isTrue(healthFloor < healthRoof, "healthFloor debe ser menor a healthRoof");

        Assert.positive(strengthFloor, "strengthFloor debe ser positivo");
        Assert.positive(strengthRoof, "strengthRoof debe ser positivo");
        Assert.isTrue(strengthFloor < strengthRoof, "strengthFloor debe ser menor a strengthRoof");

        Assert.nonNegative(inventorySize, "inventorySize debe ser mayor o igual a cero");
        Assert.nonNegative(moves, "moves debe mayor o igual a cero");

        this.random = random;
        this.healthFloor = healthFloor;
        this.healthRoof = healthRoof;
        this.strengthFloor = strengthFloor;
        this.strengthRoof = strengthRoof;
        this.inventorySize = inventorySize;
        this.moves = moves;
        this.incomingDamageFactor = incomingDamageFactor;
    }

    public CharacterData create(String name)
    {
        return new CharacterData(
                name,
                random.nextInt(healthFloor, healthRoof),
                random.nextInt(strengthFloor, strengthRoof),
                inventorySize,
                moves,
                incomingDamageFactor
        );
    }
}
