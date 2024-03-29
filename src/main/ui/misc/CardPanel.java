package ui.misc;

import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardPanel extends JPanel {

    private final Map<String, Component> panels;
    private Component activePanel;
    private final CardLayout layout;


    public CardPanel() {
        panels = new HashMap<>();
        layout = new CardLayout();
        setLayout(layout);
    }

    public CardLayout getCardLayout() {
        return layout;
    }

    @Override
    public void add(Component component, Object constraints) {
        component.setName((String) constraints);
        super.add(component, constraints);
        panels.put((String) constraints, component);
    }

    public void showPanel(String pageName) {
        layout.show(this, pageName);
        activePanel = panels.get(pageName);
        firePropertyChange(PropertyNames.WINDOW_CHANGE_EVENT, true, false);
    }

    public String getActivePanelName() {
        System.out.println(activePanel.getName());
        return activePanel.getName();
    }
}
