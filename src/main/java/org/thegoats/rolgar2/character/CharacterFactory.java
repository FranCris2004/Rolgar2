package org.thegoats.rolgar2.character;

public class CharacterFactory {
    private final int playerMaxHealth;
    private final int playerStrength;
    private final int enemyMaxHealth;
    private final int enemyStrength;
    private int nextEnemyNum = 0;

    public CharacterFactory(int playerMaxHealth, int playerStrength, int enemyMaxHealth, int enemyStrength) {
        this.playerMaxHealth = playerMaxHealth;
        this.playerStrength = playerStrength;
        this.enemyMaxHealth = enemyMaxHealth;
        this.enemyStrength = enemyStrength;
    }

    //TODO: arreglar
    public CharacterData createPlayer(String name)
    {
        return null;
    }

    public CharacterData createEnemy()
    {
        return null;
    }
}
