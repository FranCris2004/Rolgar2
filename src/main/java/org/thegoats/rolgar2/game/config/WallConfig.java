package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Wall;

import java.io.IOException;

/**
 * Configuración de un tipo de pared del mapa.
 * @param name nombre único de la pared
 * @param spritePath ruta de la imagen que se utilizará para dibujar esta pared
 * @param isClimbable indica si el personaje puede trepar esta pared
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WallConfig(String name, String spritePath, Boolean isClimbable) {

    /**
     * Constructor del record.
     * Validas las respectivas excepciones y si alguna falla lanza excepcion
     */
    public WallConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isClimbable == null) {
            isClimbable = false;
        }
    }

    /**
     * Carga y devuelve el Bitmap asociado a la ruta de la imagen de la pared.
     * @return el Bitmap cargado desde spritePath
     * @throws IOException si ocurre un error al leer el archivo de imagen
     */
    @JsonIgnore
    public Bitmap getBitmap() throws IOException {
        return Bitmap.loadFromFile(spritePath);
    }

    /**
     * Crea y devuelve un objeto Wall del mundo a partir de esta configuración.
     * @return un nuevo objeto Wall basado en esta configuración
     */
    @JsonIgnore
    public Wall getWall() {
        return new Wall(name, isClimbable);
    }

    /**
     * Compara esta configuración con otra por nombre
     * @param o objeto con el que se compara
     * @return true si o es un WallConfig con el mismo nombre, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof WallConfig && ((WallConfig) o).name().equals(name);
    }
}
