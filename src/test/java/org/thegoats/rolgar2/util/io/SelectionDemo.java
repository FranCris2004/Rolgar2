package org.thegoats.rolgar2.util.io;

import org.thegoats.rolgar2.card.Card;
import org.thegoats.rolgar2.card.FireballCard;
import org.thegoats.rolgar2.card.HealingCard;
import org.thegoats.rolgar2.card.TeleportCard;
import org.thegoats.rolgar2.util.structures.lists.LinkedList;

import java.util.List;

public class SelectionDemo {
    public static void main(String[] args) {
        //stringSelection();
        cardSelection();
    }

    public static void stringSelection() {
        List<String> strings = new LinkedList<>();
        strings.add("Uno");
        strings.add("Dos");
        strings.add("Tres");

        Selection<String> selection = new ConsoleSelection<>();
        selection
                .maxTries(3)
                .selectionHeader("Elija una opcion.")
                .selectionPrompt("Eleccion: ")
                .selectionRetryMessage("Opcion invalida. Vuelva a intentarlo")
                .selectionSuccessMessage("La eleccion se ha realizado con exito.")
                .selectionFailMessage("Demasiados intentos. La eleccion ha fallado.")
                .addAllOptions(strings)
                .select()
                .ifPresent(eleccion ->
                        System.out.println("Eleccion: " + eleccion)
                );
    }

    public static void cardSelection() {
        List<Card> cards = new LinkedList<>();
        cards.add(new HealingCard(20));
        cards.add(new HealingCard(30));
        cards.add(new FireballCard(25));
        cards.add(new TeleportCard());

        Selection<Card> selection = new ConsoleSelection<>();
        selection
                .maxTries(3)
                .selectionPrompt("Carta a usar: ")
                .selectionRetryMessage("Carta invalida. Vuelva a intentarlo")
                .addAllOptions(cards)
                .select()
                .ifPresent(eleccion ->
                        System.out.println("Eleccion: " + eleccion)
                );
    }
}
