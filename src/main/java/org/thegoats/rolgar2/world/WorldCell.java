package org.thegoats.rolgar2.world;

import org.thegoats.rolgar2.character.Character;

public class WorldCell {
    private Object content = null;

    public Object get() {
        return content;
    }

    public void set(Object content) {
        if (!validContent(content)) {
            throw new IllegalArgumentException("'content' debe ser null o una instancia de ");
        }

        this.content = content;
    }

    public boolean hasNull() {
        return content == null;
    }

    public boolean hasCharacter()
    {
        return !hasNull() && content instanceof Character;
    }

    public boolean hasBlock()
    {
        return !hasNull() && content instanceof Block;
    }

    private static boolean validContent(Object content) {
        return content == null ||
                content instanceof Block ||
                content instanceof Character;
    }
}
