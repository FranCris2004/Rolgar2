package org.thegoats.rolgar2.mundo;

public class Bloque {
	private Posicion posicion;
	private int fallDamage;
	private boolean isWalkable = true;
	
	/**
	 * 
	 * @param posicion disponible dentro del rango del mundo
	 * @param fallDamage no negativo, danio al caer en este bloque
	 * @param isWalkable si se puede caminar sobre este bloque
	 */
	public Bloque(Posicion posicion, int fallDamage, boolean isWalkable) {
		setPosicion(posicion);
		setFallDamage(fallDamage);
		setIsWalkable(isWalkable);
	}
	
	
	public void setPosicion(Posicion posicion) {
		if (posicion == null) {
			throw new NullPointerException("La posicion debe ser no nula");
		}
		this.posicion = posicion;
	}
	
	/**
	 * 
	 * @param fallDamage no negativo, danio al caer en este bloque
	 */
	public void setFallDamage(int fallDamage) {
		if (fallDamage < 0) {
			throw new IllegalArgumentException("El daño de caida debe ser mayor a cero");
		}
		this.fallDamage = fallDamage;
	}
	
	
	/**
	 * 
	 * @param isWalkable si se puede caminar sobre este bloque
	 */
	public void setIsWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}
	

	/**
	 * 
	 * @return fallDamage
	 */
	public int getFallDamage() {
		return fallDamage;
	}
	
	/**
	 * 
	 * @return isWalkable
	 */
	public boolean getIsWalkable() {
		return isWalkable;
	}
	
	
	
	
}
