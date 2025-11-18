package org.thegoats.rolgar2.util.io;

import org.thegoats.rolgar2.util.structures.maps.SetMap;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleSelection<T> extends Selection<T> {
    @Override
    public Optional<T> select() {
            var markerOptionMap = createMarkerOptionMap();

            printIfNotNull(getSelectionHeader());
            printMarkerOptionMap(markerOptionMap);

            var selection = trySelect(markerOptionMap);
            for (int tryCount = 1; tryCount < getMaxTries(); tryCount++) {
                if (selection.isPresent()) {
                    printIfNotNull(getSelectionSuccessMessage());
                    return selection;
                }

                printIfNotNull(getSelectionRetryMessage());
                selection = trySelect(markerOptionMap);
            }

            printIfNotNull(getSelectionFailMessage());
            return selection;
        }

        private Optional<T> trySelect(Map<String, SelectionOption<T>> markerOptionMap) {
            Scanner scanner = new Scanner(System.in);
            printIfNotNull(getSelectionPrompt());
            String input =  scanner.nextLine();
            var selectionOption = markerOptionMap.getOrDefault(input, null);
            return selectionOption == null ? Optional.empty() : Optional.ofNullable(selectionOption.getItemValue());
        }

        private Map<String, SelectionOption<T>> createMarkerOptionMap() {
            Map<String, SelectionOption<T>> markerOptionMap = new SetMap<>();

            int i = 0;
            for (SelectionOption<T> option : options) {
                var marker = String.valueOf(++i);
                markerOptionMap.put(marker, option);
            }

            return markerOptionMap;
        }

        private void printMarkerOptionMap(Map<String, SelectionOption<T>> markerOptionMap) {
            for (Map.Entry<String, SelectionOption<T>> entry : markerOptionMap.entrySet()) {
                System.out.printf("[%s] %s\n", entry.getKey(), entry.getValue());
            }
        }

        private static void printIfNotNull(String string) {
            if (string != null) {
                System.out.println(string);
            }
        }
}
