package ui.additionmenu;

import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DescendantAdditionMenu extends JPanel {
    private JTextField nameBox;
    private JButton addButton;
    private JPanel addedItemFrame;
    private List<AddedItemBox> addedItems;
    private GridBagConstraints constraints;

    public DescendantAdditionMenu() {
        addedItems = new ArrayList<>();
        setLayout(new BorderLayout());
        nameBox = new JTextField(5);
        GridBagLayout layout = new GridBagLayout();
        addedItemFrame = new JPanel(layout);
        setupConstraints();
//        layout.setConstraints(this, constraints);
        JPanel additionPanel = new JPanel();
        addButton = new JButton("+");
        addButton.addActionListener(e -> addDescendant(nameBox.getText()));
        additionPanel.add(nameBox);
        additionPanel.add(addButton);

        add(additionPanel, BorderLayout.NORTH);
        add(new JScrollPane(addedItemFrame), BorderLayout.CENTER);

    }

    private void setupConstraints() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.CENTER;
//        constraints.weightx = 1;
//        constraints.weighty = 1;

    }

    public void addDescendant(String name) {
        AddedItemBox newBox = new AddedItemBox(name);
        if (addedItems.contains(newBox)) {
            MineralQueryHandler.showErrorMessage("Already Added");
            return;
        }

        addedItems.add(newBox);
        newBox.addPropertyChangeListener(PropertyNames.DESCENDANT_DELETED, e -> deleteItem(e.getSource()));
        updateComponenets();

    }

    private void deleteItem(Object source) {
        AddedItemBox addBox = (AddedItemBox) source;
        addedItems.remove(addBox);
        updateComponenets();

    }

    public void updateComponenets() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        addedItemFrame.removeAll();

        for (AddedItemBox box : addedItems) {
            addedItemFrame.add(box, constraints);
            constraints.gridx = (constraints.gridx + 1) % 2;
            constraints.gridy = constraints.gridx == 0 ? constraints.gridy + 1 : constraints.gridy;
        }

        updateUI();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(400, 400);
        mainFrame.add(new DescendantAdditionMenu());
        mainFrame.setVisible(true);
    }
}
