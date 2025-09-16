package org.thegoats.rolgar2.mundo;

public class Bloque {
	private Posicion posicion;
	private boolean isWalkable;
	
	/**
	 * @param posicion No null, posicion fija del bloque
	 * @param isWalkable Si se puede caminar o no sobre este bloque
	 */
	public Bloque(Posicion posicion, boolean isWalkable) {
		if (posicion == null) {
			throw new NullPointerException("La posicion debe ser no nula");
		}
	
		this.posicion = posicion;
		this.isWalkable = isWalkable;
	}
	
	/**
     * @return posicion No null
	 */
	public Posicion getPosicion() {
		return new Posicion(posicion.fila, posicion.columna, posicion.capa);
	}
	
	public boolean getIsWalkable() {
		return isWalkable;
	}
}
