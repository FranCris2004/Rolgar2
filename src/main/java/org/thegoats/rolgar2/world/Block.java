package org.thegoats.rolgar2.world;

public class Block {
	private Position position;
	private boolean isWalkable;
	
	/**
	 * @param position No null, posicion fija del bloque
	 * @param isWalkable Si se puede caminar o no sobre este bloque
	 */
	public Block(Position position, boolean isWalkable) {
		if (position == null) {
			throw new NullPointerException("'position' debe ser no nula");
		}
	
		this.position = position;
		this.isWalkable = isWalkable;
	}
	
	/**
     * @return position No null
	 */
	public Position getPosition() {
		return position;
	}
	
	public boolean getIsWalkable() {
		return isWalkable;
	}
}
