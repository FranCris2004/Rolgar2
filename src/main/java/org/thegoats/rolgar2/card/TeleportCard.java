package org.thegoats.rolgar2.card;

public class TeleportCard implements Card {
    // TODO: Implementar

    @Override
    public void use() {}

    public static class Factory implements Card.Factory<TeleportCard> {
        @Override
        public TeleportCard create() {
            return new TeleportCard();
        }
    }

    /**
     * @return Devuelve el nombre del efecto
     */
    public String getName(){
        return "teletransporte";
    }

}
