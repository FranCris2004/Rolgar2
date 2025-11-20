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


    public String getName(){
        return "teletransporte";
    }

    /**
     *@return devuelve una version en formato String de la carta
     */
    @Override
    public String toString(){
        return "TeleportCard";
    }
}
