package org.thegoats.rolgar2.character;

import java.util.Random;

// TODO: comentar

public class CharacterFactory {
    private final Random random;
    private final int minHealth;
    private final int maxHealth;
    private final int minStrength;
    private final int maxStrength;
    private final int inventorySize;
    private final int moves;
    private final double incomingDamageFactor;

    public CharacterFactory(Random random,
                            int minHealth,
                            int maxHealth,
                            int minStrength,
                            int maxStrength,
                            int inventorySize,
                            int moves,
                            double incomingDamageFactor) {
        // TODO: agregar validaciones

        this.random = random;
        this.minHealth = minHealth;
        this.maxHealth = maxHealth;
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
        this.inventorySize = inventorySize;
        this.moves = moves;
        this.incomingDamageFactor = incomingDamageFactor;
    }

    public CharacterData create(String name)
    {
        return new CharacterData(
                name,
                random.nextInt(minHealth, maxHealth),
                random.nextInt(minStrength, maxStrength),
                inventorySize,
                moves,
                incomingDamageFactor
        );
    }
}
