package org.thegoats.rolgar2.game.config.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.FireballCard;
import org.thegoats.rolgar2.util.Assert;

import java.util.Random;

public record FireballCardConfig(
        int damageFloor,
        int damageRoof
) implements CardConfig<FireballCard> {
    public FireballCardConfig {
        Assert.positive(damageFloor, "damageFloor");
        Assert.isTrue(damageRoof > damageFloor, "damageRoof debe ser mayor a damageFloor");
    }

    @Override @JsonIgnore
    public Card.Factory<FireballCard> getFactory(Random random) {
        return new FireballCard.Factory(random, damageFloor, damageRoof);
    }
}
