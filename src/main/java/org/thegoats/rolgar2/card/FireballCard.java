package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

/**
 * Carta que arroja una bola de fuego al personaje 'target' asignado.
 */
public class FireballCard extends CardWithCharacterTarget {
    private Integer damage = null;

    /**
     * Chequea que a la carta ya se le haya asignado un personaje 'target' objetivo, y que se le haya setteado un
     * damage valido.
     * Si ambas condiciones se cumplen, daña a 'target' en funcion de damage
     */
    @Override
    public void use() {
        validateTarget();
        Assert.notNull(damage, "damage no ha sido setteado.");
        getTarget().takeDamage(damage);
    }

    /**
     * Si damage recibido por parametro es valido, lo setea
     * @param damage mayor a cero, daño que hara la bola de fuego
     */
    public void setDamage(int damage) {
        Assert.positive(damage, "damage debe ser positivo.");
        this.damage = damage;
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    @Override
    public String toString(){
        return String.format("FireballCard[target=%s, damage=%d]",
                getTarget().toString(),
                damage);
    }
}
