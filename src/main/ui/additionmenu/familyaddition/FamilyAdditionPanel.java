package ui.additionmenu.familyaddition;

import utils.fieldnames.AttributeNames;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyAdditionPanel extends JPanel {
    private final JTextField familyName;
    private final JTextField familyFormula;
    private final DescendantAdditionMenu descendantAdditionMenu;

    GridBagConstraints constraints;


    public FamilyAdditionPanel() {
        familyName = new JTextField(8);
        familyFormula = new JTextField(12);
        descendantAdditionMenu = new DescendantAdditionMenu();

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

    private void setupBoxes() {
        for (JComponent component : getAttributes()) {
            add(component, constraints);
            constraints.gridy++;
        }
    }

    private void setupLabels() {
        for (String label : AttributeNames.FAMILY_ATTRIBUTE_NAMES) {
            add(new JLabel(label + ":"), constraints);
            constraints.gridy++;
        }

        constraints.gridy = 0;
    }

    public JComponent[] getAttributes() {
        return new JComponent[]{familyName, familyFormula, descendantAdditionMenu};
    }


    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(400, 400);
        mainFrame.add(new FamilyAdditionPanel());
        mainFrame.setVisible(true);
    }

}
