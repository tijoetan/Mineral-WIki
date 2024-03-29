package ui.clickeditemhandler;

import model.entries.WikiEntry;

import java.util.ArrayList;
import java.util.List;

// singleton that stores the most recently clicked item

public class ClickedItemHandler {
    private static ClickedItemHandler instance;

    private WikiEntry clickedItem;
    private final List<ClickObserver> clickObservers;

    // EFFECTS: private instance constructor
    private ClickedItemHandler() {
        clickedItem = null;
        clickObservers = new ArrayList<>();
    }

    // getters
    public WikiEntry getClickedItem() {
        return clickedItem;
    }

    // MODIFIES: this
    // EFFECTS: sets this.clickedItem to clickedItem
    public void setClickedItem(WikiEntry clickedItem) {
        this.clickedItem = clickedItem;
        notifyObservers();
    }

    // MODIFIES: this
    // EFFECTS: adds observer to clickObservers
    public void addObserver(ClickObserver observer) {
        clickObservers.add(observer);
    }

    // EFFECTS: calls update() on all registered observers
    private void notifyObservers() {
        for (ClickObserver observer : clickObservers) {
            observer.update();
        }
    }

    // EFFECTS: returns single instance of ClickedItemHandler
    public static ClickedItemHandler getInstance() {
        if (instance == null) {
            instance = new ClickedItemHandler();
        }

        return instance;
    }


}
