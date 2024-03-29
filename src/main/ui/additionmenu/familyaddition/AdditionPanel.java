package ui.additionmenu.familyaddition;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AdditionPanel extends JToolBar {
    private final JTextField nameBox;
    private final JButton addButton;

    public AdditionPanel() {
        nameBox = new JTextField(5);
        addButton = new JButton("+");
        setFloatable(false);
        add(new JLabel("Name: "));
        add(nameBox);
        add(addButton);
    }

    public void addButtonActionListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void clearText() {
        nameBox.setText("");
    }

    public String getText() {
        return nameBox.getText();
    }
}