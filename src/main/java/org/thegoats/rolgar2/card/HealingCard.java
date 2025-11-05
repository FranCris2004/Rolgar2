package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

/**
 * Carta que cura al personaje 'target' en 'healingPoints' unidades
 */
public class HealingCard extends CardWithCharacterTarget {
    private Integer healingPoints = null;

    /**
     * Chequea que a la carta ya se le haya asignado un personaje 'target' objetivo, y que se le haya setteado unos
     * healingPoints validos.
     * Si ambas condiciones se cumplen, daña a 'target' en funcion de damage
     */
    @Override
    public void use() {
        validateTarget();
        Assert.notNull(healingPoints, "healingPoints no fue setteado");
        getTarget().recoverHealth(healingPoints);
    }

    /**
     * Si healingPoints recibido por parametro es valido, lo setea
     * @param healingPoints mayor a cero, puntos de vida a curarle a 'target'
     */
    public void setHealingPoints(int healingPoints) {
        Assert.positive(healingPoints, "healingPoints debe ser positiva.");
        this.healingPoints = healingPoints;
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    public String toString(){
        return String.format("HealingCard[target=%s, healingPoints=%d",
                getTarget().toString(),
                healingPoints);
    }
}
