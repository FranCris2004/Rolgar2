package org.thegoats.rolgar2.world;

public record Block(Position position, boolean isWalkable) {
	/**
	 * @param position No null, posicion fija del bloque
	 * @param isWalkable Si se puede caminar o no sobre este bloque
	 */
	public Block {
		if (position == null) {
			throw new NullPointerException("'position' debe ser no nula");
		}
	}
}
