package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.character.CharacterFactory;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 * define los rangos y parámetros base que se usarán para crear
 * un personaje en el juego (por ejemplo, el jugador o un enemigo)
 * @param healthFloor minimo de vida posible
 * @param healthRoof maximo de vida posible
 * @param strengthFloor minimo de fuerza posible
 * @param strengthRoof maximo de fuerza posible
 * @param inventorySize tamaño del inventario del personaje
 * @param moves  cantidad de movimientos disponibles por turno
 * @param incomingDamageFactor factor que multiplica el daño que recibe el personaje
 */
public record CharacterConfig(
        int healthFloor,
        int healthRoof,
        int strengthFloor,
        int strengthRoof,
        int inventorySize,
        int moves,
        double incomingDamageFactor
) {
    /**
     * constructor  del record.
     * valida las correspodientes validaciones para cada campo. Si no se cumple lanza excepcion
     */
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

    /**
     * Crea y devuelve un CharacterFactory basada en esta configuracion
     * @param random generador de números aleatorios que se usará para elegir valores concretos de vida y fuerza
     * @return un objeto CharacterFactory configurado con estos parámetros
     */
    @JsonIgnore
    public CharacterFactory getCharacterFactory(Random random) {
        return new CharacterFactory(random, this);
    }
}
