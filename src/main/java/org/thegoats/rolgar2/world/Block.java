package org.thegoats.rolgar2.world;

/**
 * Representa un bloque<br>
 * NOTA: Por ahora solo contiene el atributo isWalkable, en el futuro quizas se agreguen mas
 */
public class Block {
	public boolean isWalkable;

	/**
	 * @param isWalkable true para que se pueda caminar sobre este bloque, false para que no se pueda
	 */
	public Block(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}
}
