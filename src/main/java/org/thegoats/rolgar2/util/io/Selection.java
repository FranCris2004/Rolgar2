package org.thegoats.rolgar2.util.io;

import org.thegoats.rolgar2.util.Assert;

import java.util.*;

public abstract class Selection<T> {
    public static class SelectionOption<T> {
        private final T item;

        private SelectionOption(T item) {
            Assert.notNull(item, "item");
            this.item = item;
        }

        public T getItemValue() {
            return item;
        }

        @Override
        public String toString() {
            return item.toString();
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    public static class PresetStringSelectionOption<T> extends SelectionOption<T> {
        private final String itemString;

        private PresetStringSelectionOption(T item, String itemString) {
            super(item);
            Assert.notNull(itemString, "itemString");
            this.itemString = itemString;
        }

        @Override
        public String toString() {
            return itemString;
        }
    }

    protected final Set<SelectionOption<T>> options = new org.thegoats.rolgar2.util.structures.sets.Set<>();

    private int maxTries = 1;

    private String selectionHeader = null;
    private String selectionPrompt = null;
    private String selectionSuccessMessage = null;
    private String selectionFailMessage = null;
    private String selectionRetryMessage = null;

    public int getMaxTries() {
        return maxTries;
    }

    public String getSelectionHeader() {
        return selectionHeader;
    }

    public String getSelectionPrompt() {
        return selectionPrompt;
    }

    public String getSelectionSuccessMessage() {
        return selectionSuccessMessage;
    }

    public String getSelectionFailMessage() {
        return selectionFailMessage;
    }

    public String getSelectionRetryMessage() {
        return selectionRetryMessage;
    }

    public Selection<T> maxTries(int maxTries) {
        Assert.positive(maxTries, "maxTries");
        this.maxTries = maxTries;
        return this;
    }

    public Selection<T> selectionHeader(String selectHeader) {
        this.selectionHeader = selectHeader;
        return this;
    }

    public Selection<T> selectionPrompt(String selectPrompt) {
        this.selectionPrompt = selectPrompt;
        return this;
    }

    public Selection<T> selectionSuccessMessage(String successOnSelectMessage) {
        this.selectionSuccessMessage = successOnSelectMessage;
        return this;
    }

    public Selection<T> selectionFailMessage(String failOnSelectMessage) {
        this.selectionFailMessage = failOnSelectMessage;
        return this;
    }

    public Selection<T> selectionRetryMessage(String retryMessage) {
        this.selectionRetryMessage = retryMessage;
        return this;
    }

    public Selection<T> addOption(T item) {
        options.add(new SelectionOption<>(item));
        return this;
    }

    public Selection<T> addOption(String itemString, T item) {
        options.add(new PresetStringSelectionOption<>(item, itemString));
        return this;
    }

    public Selection<T> addAllOptions(List<T> items) {
        for (T item : items) {
            addOption(item);
        }
        return this;
    }

    public Selection<T> addAllOptions(List<T> items, List<String> itemStrings) {
        if (items.size() != itemStrings.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < items.size(); i++) {
            addOption(itemStrings.get(i), items.get(i));
        }

        return this;
    }

    public abstract Optional<T> select();
}
