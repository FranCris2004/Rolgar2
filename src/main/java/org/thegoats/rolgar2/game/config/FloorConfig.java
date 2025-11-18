package org.thegoats.rolgar2.game.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.thegoats.rolgar2.util.Assert;
import org.thegoats.rolgar2.util.io.Bitmap;
import org.thegoats.rolgar2.world.Floor;

import java.io.IOException;

/**
 * Configuración de un tipo de piso del mapa.
 * @param name nombre único del piso
 * @param spritePath ruta de la imagen que se utilizará para dibujar este piso
 * @param isWalkable indica si el personaje puede caminar sobre este piso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FloorConfig(String name, String spritePath, Boolean isWalkable) {


    /**
     * Constructor del record.
     * realiza las validaciones correspondiente, si alguna falla lanza excepcion
     */
    public FloorConfig {
        Assert.notNull(name, "name no puede ser nulo");
        Assert.notNull(spritePath, "spritePath no puede ser nulo");
        Assert.fileExists(spritePath, "No existe el archivo: " + spritePath);

        if (isWalkable == null) {
            isWalkable = false;
        }
    }

    /**
     * Carga y devuelve el Bitmap asociado a la ruta de la imagen del piso.
     * @return el Bitmap cargado desde spritePath
     * @throws IOException si ocurre un error al leer el archivo de imagen
     */
    @JsonIgnore
    public Bitmap getBitmap() throws IOException {
        return Bitmap.loadFromFile(spritePath);
    }

    /**
     * Crea y devuelve un objeto Floor del mundo a partir de esta configuración.
     * @return un nuevo objeto Floor basado en esta configuración
     */
    @JsonIgnore
    public Floor getFloor() {
        return new Floor(name, isWalkable);
    }

    /**
     * Compara esta configuración con otra por nombre.
     * @param o objeto con el que se compara
     * @return true si o es un FloorConfig con el mismo nombre, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof FloorConfig && ((FloorConfig) o).name.equals(name);
    }
}
