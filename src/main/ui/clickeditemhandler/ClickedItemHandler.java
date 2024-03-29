package ui.clickeditemhandler;

import model.entries.WikiEntry;

import java.util.ArrayList;
import java.util.List;

public class ClickedItemHandler {
    private static ClickedItemHandler instance;

    private WikiEntry clickedItem;
    private final List<ClickObserver> clickObservers;

    private ClickedItemHandler() {
        clickedItem = null;
        clickObservers = new ArrayList<>();
    }

    public void setClickedItem(WikiEntry clickedItem) {
        this.clickedItem = clickedItem;
        notifyObservers();
    }

    public void addObserver(ClickObserver observer) {
        clickObservers.add(observer);
    }

    private void notifyObservers() {
        for (ClickObserver observer : clickObservers) {
            observer.update();
        }
    }

    public WikiEntry getClickedItem() {
        return clickedItem;
    }

    public static ClickedItemHandler getInstance() {
        if (instance == null) {
            instance = new ClickedItemHandler();
        }

        return instance;
    }


}
