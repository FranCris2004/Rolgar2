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
	public int hashCode() {
		return Objects.hash(isClimbable, isWalkable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		return isClimbable == other.isClimbable && isWalkable == other.isWalkable;
	}

	
}
