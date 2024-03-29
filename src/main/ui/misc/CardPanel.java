package ui.misc;

import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// class for containing multiple views

public class CardPanel extends JPanel {

    private final Map<String, Component> panels;
    private Component activePanel;
    private final CardLayout layout;

    // EFFECTS: makes new CardPanel with no current panels
    public CardPanel() {
        panels = new HashMap<>();
        layout = new CardLayout();
        setLayout(layout);
    }

    // getters
    public String getActivePanelName() {
        System.out.println(activePanel.getName());
        return activePanel.getName();
    }

    // MODIFIES: this
    // EFFECTS: adds provided component and adds name/componenet pair to panels
    @Override
    public void add(Component component, Object name) {
        component.setName((String) name);
        super.add(component, name);
        panels.put((String) name, component);
    }

    // MODIFIES: this
    // EFFECTS: displays panel associated with provided pageName
    public void showPanel(String pageName) {
        layout.show(this, pageName);
        activePanel = panels.get(pageName);
        firePropertyChange(PropertyNames.WINDOW_CHANGE_EVENT, true, false);
    }

}
