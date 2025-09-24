package org.thegoats.rolgar2.entity.character.player;

import org.thegoats.rolgar2.entity.character.Character;
import org.thegoats.rolgar2.world.Position;

public final class Player extends Character {
    /**
     * @param name      no nulo, sólo debe contener de 3 a 20 caracteres alfanuméricos, '.' , '-' y '_'
     * @param maxHealth Debe ser mayor a 0
     * @param health    Debe ser mayor o igual a 0
     * @param strength  Debe ser mayor o igual a 0
     * @param position  No null
     */
    Player(String name, int maxHealth, int health, int strength, Position position) {
        super(name, maxHealth, health, strength, position);
    }
}
