package ui.additionmenu.familyaddition;

import javax.swing.*;
import java.awt.event.ActionListener;

// Panel that allows user to enter Family descendants

public class AdditionPanel extends JToolBar {
    private final JTextField nameBox;
    private final JButton addButton;

    // EFFECTS: Makes new AdditionPanel
    public AdditionPanel() {
        nameBox = new JTextField(5);
        addButton = new JButton("+");
        setFloatable(false);
        add(new JLabel("Name: "));
        add(nameBox);
        add(addButton);
    }

    // getters
    public String getText() {
        return nameBox.getText();
    }

    // MODIFIES: this
    // EFFECTS: Adds listener to addButton
    public void addButtonActionListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: clears the nameBox text
    public void clearText() {
        nameBox.setText("");
    }

}