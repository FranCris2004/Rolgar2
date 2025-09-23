package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.util.Assert;

public record Block(Position position, boolean isWalkable) {
	/**
	 * @param position No null, posicion bloque
	 * @param isWalkable Si se puede caminar o no sobre este bloque
	 */
	public Block {
		Assert.notNull(position, "'position' debe ser no nula");
	}
}
