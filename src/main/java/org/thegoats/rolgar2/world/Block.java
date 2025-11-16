package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.util.Assert;

import java.util.Objects;

/**
 * Representa un bloque<br>
 * NOTA: Por ahora solo contiene el atributo isWalkable, en el futuro quizas se agreguen mas
 */
public class Block {
	private boolean isWalkable; // no es necesario getter y setter

	/**
	 * @param isWalkable true para que se pueda caminar sobre este bloque, false para que no se pueda
	 */
	public Block(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

    /**
     * @return version en formato String del bloque
     */
	@Override
	public String toString() {
		return String.format("Block[isWalkable=%b]", isWalkable);
	}

    /**
     * @param o el otro bloque con el que comparar
     * @return true si se consideran iguales, es decir, si son la misma referencia o si ambos son caminables
     */
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Block block = (Block) o;
		return this == block ||
                isWalkable == block.isWalkable;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(isWalkable);
	}

    /**
     * @return true si es caminable
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * Vuelve caminable a {@code this}
     * @throws RuntimeException si {@code this} ya era caminable
     */
    public void setWalkable() {
        Assert.isTrue(!isWalkable(), "no debe ser caminable");
        this.isWalkable = true;
    }


    /**
     * Vuelve no caminable a {@code this}
     * @throws RuntimeException si {@code this} no era caminable
     */
    public void setNotWalkable() {
        Assert.isTrue(isWalkable(), "debe ser caminable");
        this.isWalkable = false;
    }
}
