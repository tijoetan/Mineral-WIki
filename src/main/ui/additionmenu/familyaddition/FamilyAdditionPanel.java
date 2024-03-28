package ui.additionmenu.familyaddition;

import model.entries.Family;
import model.entries.WikiEntry;
import utils.fieldnames.AttributeNames;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyAdditionPanel extends JPanel {
    private final JTextField familyName;
    private final JTextField familyFormula;
    private final JTextArea description;
    private final DescendantAdditionMenu descendantAdditionMenu;

    GridBagConstraints constraints;


    public FamilyAdditionPanel() {
        familyName = new JTextField(8);
        familyFormula = new JTextField(12);
        descendantAdditionMenu = new DescendantAdditionMenu();
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
                .map(AddedItemBox::getName)
                .collect(Collectors.toList());
    }

    public void configurePanelBy(Family family) {
        familyName.setText(family.getName());
        familyName.setEnabled(false);

        familyFormula.setText(family.getGeneralFormula().getUnparsedFormula());
        for (WikiEntry descendant : family.getMineralsWithFamily()) {
            descendantAdditionMenu.addDescendant(descendant.getName());
        }
    }

    private void setupBoxes() {
        for (JComponent component : getAttributes()) {
            add(component, constraints);
            constraints.gridy++;
        }
        add(new JScrollPane(description), constraints);
    }

    private void setupLabels() {
        for (String label : AttributeNames.FAMILY_ATTRIBUTE_NAMES) {
            add(new JLabel(label + ":"), constraints);
            constraints.gridy++;
        }
        add(new JLabel("Description:"), constraints);
        constraints.gridy = 0;
    }

    public JComponent[] getAttributes() {
        return new JComponent[]{familyName, familyFormula, descendantAdditionMenu};
    }



}
