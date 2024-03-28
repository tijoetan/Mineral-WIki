package ui.additionmenu.familyaddition;

import utils.UserQuery;
import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DescendantAdditionMenu extends JPanel {
    private final JTextField nameBox;
    private final JButton addButton;
    private final JPanel addedItemFrame;
    private final List<AddedItemBox> addedItems;
    private GridBagConstraints constraints;
    JScrollPane pane;

    public DescendantAdditionMenu() {
        addedItems = new ArrayList<>();
        setLayout(new BorderLayout());
        nameBox = new JTextField(5);
        GridBagLayout layout = new GridBagLayout();
        setupConstraints();
//        layout.setConstraints(this, constraints);
        JToolBar additionPanel = new JToolBar();
        additionPanel.setFloatable(false);

        addedItemFrame = new JPanel(layout);
        addButton = new JButton("+");
        addButton.addActionListener(e -> addDescendant(nameBox.getText()));

        additionPanel.add(new JLabel("Name: "));
        additionPanel.add(nameBox);
        additionPanel.add(addButton);


        pane = new JScrollPane(addedItemFrame);
        pane.setPreferredSize(new Dimension(240, 100));
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(additionPanel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);


    }

    private void setupConstraints() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
//        constraints.weighty = 1;

    }

    public List<AddedItemBox> getAddedItems() {
        return new ArrayList<>(addedItems);
    }


    public void addDescendant(String name) {
        AddedItemBox newBox = new AddedItemBox(name);
        if (addedItems.contains(newBox)) {
            UserQuery.showErrorMessage("Already Added");
            return;
        }

        addedItems.add(newBox);
        newBox.addPropertyChangeListener(PropertyNames.DESCENDANT_DELETED, e -> deleteItem(e.getSource()));
        updateComponents();

    }

    private void deleteItem(Object source) {
        AddedItemBox addBox = (AddedItemBox) source;
        addedItems.remove(addBox);
        updateComponents();

    }

    public void updateComponents() {
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        addedItemFrame.removeAll();

        for (AddedItemBox box : addedItems) {
            addedItemFrame.add(box, constraints);
            constraints.gridx = (constraints.gridx + 1) % 2;
            constraints.gridy = constraints.gridx == 0 ? constraints.gridy + 1 : constraints.gridy;
        }

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        addedItemFrame.add(new JLabel(""), constraints);

        revalidate();
        repaint();
    }

}
