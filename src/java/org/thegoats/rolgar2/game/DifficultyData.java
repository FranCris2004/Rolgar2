package org.thegoats.rolgar2.game;

public record DifficultyData(
		String name,
		int healthFloor,
		int healthRoof,
		int strengthFloor,
		int strengthRoof,
		int inventorySize,
		int moves,
		double incomingDamageFactor) {
}
