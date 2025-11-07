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

public record DoubleMoveCardData(int remainingTurnsFloor,
                                 int remainingTurnsRoof){
}

public record FireballCardData(int damageFloor,
                               int damageRoof){
}

public record HealingCardData(int healingPointsFloor,
                              int healingPointsRoof){
}

public record IceballCardData(int remainingTurnsFloor,
                              int remainingTurnsRoof){
}

public record InvisibilityCardData(int remainingTurnsFloor,
                                   int remainingTurnsRoof){
}

public record ShieldCardData(int remainingTurnsFloor,
                             int remainingTurnsRoof,
                             double incomingDamageFactorModifierFloor,
                             double incomingDamageFactorModifierRoof){
}

public record StrengthCardData(int remainingTurnsFloor,
                               int remainingTurnsRoof){
}

