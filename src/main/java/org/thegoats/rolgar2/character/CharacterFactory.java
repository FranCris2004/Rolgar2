package org.thegoats.rolgar2.character;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

// TODO: comentar

/**
 *  Fábrica de Character
 */
public class CharacterFactory {
    private final Random random;
    private final int healthFloor;
    private final int healthRoof;
    private final int strengthFloor;
    private final int strengthRoof;
    private final int inventorySize;
    private final int moves;
    private final double incomingDamageFactor;

    /**
     * Crea una fábrica de Character
     * @param random no null, generador de valores
     * @param healthFloor mayor a cero y menor a healthRoof piso de la vida maxima
     * @param healthRoof mayor a cero y mayor a healthFloor, techo de la vida maxima
     * @param strengthFloor mayor a cero y menor a strengthRoof, piso de strength
     * @param strengthRoof mayor a cero y mayor a strengthFloor, techo de strength
     * @param inventorySize mayor a cero, tamaño máximo del inventario
     * @param moves mayor a cero, movimientos totales por turno
     * @param incomingDamageFactor mayor a cero, multiplicador de daño recibido
     */
    public CharacterFactory(Random random,
                            int healthFloor,
                            int healthRoof,
                            int strengthFloor,
                            int strengthRoof,
                            int inventorySize,
                            int moves,
                            double incomingDamageFactor) {
        Assert.notNull(random, "random no puede ser null");
        Assert.positive(healthFloor, "healthFloor debe ser positivo");
        Assert.positive(healthRoof, "healthRoof debe ser positivo");
        Assert.isTrue(healthFloor < healthRoof, "healthFloor debe ser menor a healthRoof");

        Assert.positive(strengthFloor, "strengthFloor debe ser positivo");
        Assert.positive(strengthRoof, "strengthRoof debe ser positivo");
        Assert.isTrue(strengthFloor < strengthRoof, "strengthFloor debe ser menor a strengthRoof");

        Assert.positive(inventorySize, "inventorySize debe ser mayor a cero");
        Assert.positive(moves, "moves debe mayor a cero");
        Assert.positive(incomingDamageFactor, "incomingDamageFactor debe ser positivo");

        this.random = random;
        this.healthFloor = healthFloor;
        this.healthRoof = healthRoof;
        this.strengthFloor = strengthFloor;
        this.strengthRoof = strengthRoof;
        this.inventorySize = inventorySize;
        this.moves = moves;
        this.incomingDamageFactor = incomingDamageFactor;
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
                random.nextInt(healthFloor, healthRoof),
                random.nextInt(strengthFloor, strengthRoof),
                inventorySize,
                moves,
                incomingDamageFactor
        );
    }
}
