package org.thegoats.rolgar2.game;

import org.thegoats.rolgar2.character.CharacterData;

/**
 * Interfaz de las clases CharacterController,
 * las cuales son las que contienen la logica de control del personaje y si es necesario, toman el input del usuario
 */
public interface CharacterController {
    void doTurn(CharacterData character);
}
