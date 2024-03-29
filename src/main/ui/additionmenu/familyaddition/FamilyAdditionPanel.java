package ui.additionmenu.familyaddition;


import model.entries.Family;
import model.entries.WikiEntry;
import ui.misc.DescendantMenu;
import utils.fieldnames.AttributeNames;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

// Panel for specifying a Family

public class FamilyAdditionPanel extends JPanel {
    private final JTextField familyName;
    private final JTextField familyFormula;
    private final JTextArea description;
    private final DescendantMenu descendantAdditionMenu;

    GridBagConstraints constraints;

    // EFFECTS: Constructs new FamilyAdditionPanel
    public FamilyAdditionPanel() {
        familyName = new JTextField(8);
        familyFormula = new JTextField(12);
        descendantAdditionMenu = new DescendantMenu(2);
        description = new JTextArea(6, 30);
        description.setLineWrap(true);

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 2, 10, 2);

        setupLabels();
        constraints.gridx++;
        setupBoxes();

    }

    // getters
    public String getDescription() {
        return description.getText();
    }

    public String getFamilyName() {
        return familyName.getText();
    }

    public String getFormula() {
        return familyFormula.getText();
    }

    public List<String> getDescendants() {
        return descendantAdditionMenu.getAddedItems()
                .stream()
                .map(JComponent::getName)
                .collect(Collectors.toList());
    }

    // MODIFIES: this
    // EFFECTS: initializes fields based on those from provided family
    public void configurePanelBy(Family family) {
        familyName.setText(family.getName());
        familyName.setEnabled(false);

        familyFormula.setText(family.getGeneralFormula().getUnparsedFormula());
        for (WikiEntry descendant : family.getMineralsWithFamily()) {
            descendantAdditionMenu.addDescendant(descendant.getName());
        }

        description.setText(family.getDescription());

        repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds areas for family specification
    private void setupBoxes() {
        for (JComponent component : getAttributes()) {
            add(component, constraints);
            constraints.gridy++;
        }
        add(new JScrollPane(description), constraints);
    }

    // MODIFIES: this
    // EFFECTS: adds labels to Panel
    private void setupLabels() {
        for (String label : AttributeNames.FAMILY_ATTRIBUTE_NAMES) {
            add(new JLabel(label + ":"), constraints);
            constraints.gridy++;
        }
        add(new JLabel("Description:"), constraints);
        constraints.gridy = 0;
    }

    // EFFECTS: Produces array with all the user boxes
    public JComponent[] getAttributes() {
        return new JComponent[]{familyName, familyFormula, descendantAdditionMenu};
    }



}
