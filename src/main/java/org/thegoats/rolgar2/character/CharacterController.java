package org.thegoats.rolgar2.character;

/**
 * Interfaz de las clases CharacterController,
 * las cuales son las que contienen la logica de control del personaje y si es necesario, toman el input del usuario
 */
public interface CharacterController {
    void doTurn(CharacterData character);
}
