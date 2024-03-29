package ui.additionmenu.familyaddition;

import model.entries.WikiEntry;
import utils.UserQuery;
import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DescendantAdditionMenu extends JPanel {
    private final JPanel addedItemFrame;
    private final List<JComponent> addedItems;
    private final AdditionPanel additionPanel;
    private GridBagConstraints constraints;
    JScrollPane pane;
    private final int rowCount;

    private int defaultConstraints;

    public DescendantAdditionMenu(int rowCount) {
        defaultConstraints = GridBagConstraints.NONE;
        this.rowCount = rowCount;
        addedItems = new ArrayList<>();
        setLayout(new BorderLayout());
        GridBagLayout layout = new GridBagLayout();
        setupConstraints();
        layout.setConstraints(this, constraints);

        addedItemFrame = new JPanel(layout);
        additionPanel = new AdditionPanel();
        additionPanel.addButtonActionListener(e -> addDescendant(additionPanel.getText()));


        pane = new JScrollPane(addedItemFrame);
        pane.setPreferredSize(new Dimension(240, 100));
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(additionPanel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);

    }

    public DescendantAdditionMenu(int rowCount, List<WikiEntry> entries) {
        this(rowCount);
        remove(additionPanel);
        defaultConstraints = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        pane.setPreferredSize(new Dimension(300, 400));
        for (WikiEntry entry : entries) {
            addDescendant(entry);
        }
    }



    private void setupConstraints() {
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
//        constraints.weighty = 1;

    }

    public List<JComponent> getAddedItems() {
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
        additionPanel.clearText();
        updateComponents();
    }

    public void addDescendant(WikiEntry entry) {
        EntryHyperLink link = new EntryHyperLink(entry);
        if (!addedItems.contains(link)) {
            addedItems.add(link);
            link.addPropertyChangeListener(PropertyNames.ITEM_CLICKED, e -> gotoView(e.getSource()));
            updateComponents();
        }
    }

    private void gotoView(Object source) {
        EntryHyperLink link = (EntryHyperLink) source;
        System.out.println(Arrays.toString(link.getEntry().giveAttributeAsObjects()));
    }

    private void deleteItem(Object source) {
        AddedItemBox addBox = (AddedItemBox) source;
        addedItems.remove(addBox);
        updateComponents();

    }

    public void updateComponents() {
        constraints.fill = defaultConstraints;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        addedItemFrame.removeAll();

        for (JComponent box : addedItems) {
            addedItemFrame.add(box, constraints);
            constraints.gridx = (constraints.gridx + 1) % rowCount;
            constraints.gridy = constraints.gridx == 0 ? constraints.gridy + 1 : constraints.gridy;
        }

        constraints.gridx = rowCount - 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        addedItemFrame.add(new JLabel(""), constraints);

        revalidate();
        repaint();
    }

}
