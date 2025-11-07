package org.thegoats.rolgar2.card;

import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

/**
 * Carta que arroja una bola de fuego al personaje 'target' asignado.
 */
public class FireballCard extends CardWithCharacterTarget {
    private final int damage;

    /**
     * Construye la carta de bola de fuego con el daño indicado
     * @param damage positivo, daño que hará la bola de fuego
     */
    public FireballCard(int damage) {
        Assert.positive(damage, "damage debe ser positivo.");
        this.damage = damage;
    }

    /**
     * Chequea que a la carta ya se le haya asignado un personaje 'target' objetivo, y que se le haya setteado un
     * damage válido.
     * Si ambas condiciones se cumplen, daña a 'target' en funcion de damage
     */
    @Override
    public void use() {
        validateTarget();
        getTarget().takeDamage(damage);
    }

    public static class Factory implements Card.Factory<FireballCard> {
        private final Random random;
        private final int damageFloor;
        private final int damageRoof;

        /**
         * Construye la fabrica de cartas de bola de fuego
         * @param random no null, objeto generador aleatorio
         * @param damageFloor mayor a cero, menor a 'damageRoof'
         * @param damageRoof mayor a cero, mayor a 'damageFloor'
         */
        public Factory(Random random, int damageFloor, int damageRoof) {
            Assert.notNull(random, "random no puede ser nulo.");
            Assert.positive(damageFloor, "damageFloor debe ser positivo.");
            Assert.positive(damageRoof, "damageRoof debe ser positivo.");
            Assert.isTrue(damageFloor <= damageRoof, "damageFloor debe ser menor o igual a damageRoof.");

            this.random = random;
            this.damageFloor = damageFloor;
            this.damageRoof = damageRoof;
        }

        /**
         * Crea una carta de bola de fuego con daño aleatoria entre 'damageFloor' y 'damageRoof'
         * pasados por parametro al constructor de Factory
         * @return carta de bola de fuego
         */
        @Override
        public FireballCard create() {
            return new FireballCard(random.nextInt(damageFloor, damageRoof));
        }
    }

    /**
     * Devuelve una version en formato String de la carta, con el formato
     * NombreDeCarta[atributo1=valor1, atributo2=valor2, ..., atributoN=valorN]
     * @return version en formato string de la carta
     */
    @Override
    public String toString(){
        return String.format("FireballCard[target=%s, damage=%d]",
                getTarget(),
                damage);
    }
}
