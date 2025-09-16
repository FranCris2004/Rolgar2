package bloque;

public class Bloque {
	private int posX, posY, posZ;
	private int fallDamage;
	private boolean isWalkable = true;
	
	/**
	 * 
	 * @param posX no negativo
	 * @param posY no negativo
	 * @param posZ no negativo
	 * @param fallDamage no negativo, danio al caer en este bloque
	 * @param isWalkable si se puede caminar sobre este bloque
	 */
	public Bloque(int posX, int posY, int posZ, int fallDamage, boolean isWalkable) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		setFallDamage(fallDamage);
		setIsWalkable(isWalkable);
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
