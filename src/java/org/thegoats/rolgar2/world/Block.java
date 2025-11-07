package org.thegoats.rolgar2.world;

import java.util.Objects;

/**
 * Representa un bloque<br>
 * NOTA: Por ahora solo contiene el atributo isWalkable, en el futuro quizas se agreguen mas
 */
public class Block {
	public boolean isWalkable; // no es necesario getter y setter
	public boolean isClimbable;
	
	/**
	 * @param isWalkable true para que se pueda caminar sobre este bloque, false para que no se pueda
	 */
	public Block(boolean isWalkable, boolean isClimbable) {
		this.isWalkable = isWalkable;
		this.isClimbable = isClimbable;
	}

	@Override
	public String toString() {
		return String.format("Block[isWalkable=%b, isClimbable=%b]", isWalkable, isClimbable);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Block block = (Block) o;
		return isWalkable == block.isWalkable && isClimbable == block.isClimbable;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(isWalkable, isClimbable);
	}
}
