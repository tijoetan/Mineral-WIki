package ui.additionmenu.mineraladdition;

import model.entries.Mineral;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import utils.fieldnames.AttributeNames;

import javax.swing.*;
import java.awt.*;

public class MineralAdditionPanel extends JPanel {

    private JTextField name;
    private JTextArea textDescription;
    private JScrollPane description; // description
    private JComboBox<CrystalStructure> crystalStructure;
    private JComboBox<Cleavage> cleavage;
    private JTextField hardness;
    private JTextField ior;
    private JTextField density;
    private JTextField formula;

    GridBagConstraints constraints;

    public MineralAdditionPanel() {

        firePropertyChange("Change", true, true);
        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        constraints.insets = new Insets(5, 0, 5, 5);
        constraints.weighty = 0;
        initializeFields();
        addFieldsToPanel();
    }

    public String getName() {
        return name.getText().trim();
    }

    public String getDescription() {
        JTextArea area = (JTextArea) description.getViewport().getView();
        return area.getText().trim();
    }

    public Object getCrystalStructure() {
        return crystalStructure.getSelectedItem();
    }

    public Object getCleavage() {
        return cleavage.getSelectedItem();
    }

    public String getHardness() {
        return hardness.getText();
    }

    public String getIor() {
        return ior.getText();
    }

    public String getDensity() {
        return density.getText();
    }

    public String getFormula() {
        return formula.getText();
    }

    private void initializeFields() {

        name = new JTextField(10);


        textDescription = new JTextArea(6, 30);

        textDescription.setLineWrap(true);
        textDescription.setMaximumSize(new Dimension(1, 1));
        description = new JScrollPane(textDescription);

        crystalStructure = new JComboBox<>(CrystalStructure.values());
        cleavage = new JComboBox<>(Cleavage.values());
        hardness = new JTextField(3);
        ior = new JTextField(3);
        density = new JTextField(3);
        formula = new JTextField(8);
    }

    private void addFieldsToPanel() {
        for (String attribute : AttributeNames.MINERAL_ATTRIBUTE_NAMES) {
            add(new JLabel(attribute + ":"), constraints);
            constraints.gridy++;
        }

        constraints.gridy = 0;
        constraints.gridx++;
        for (JComponent component : produceComponentArray()) {
            add(component, constraints);
            constraints.gridy++;
        }

        constraints.gridx = 0;
        add(new JLabel("Description:"), constraints);
        constraints.gridx++;
        add(description, constraints);
    }

    public JComponent[] produceComponentArray() {
        return new JComponent[]{name, crystalStructure, formula, hardness, density, ior, cleavage};
    }

    public void setFields(Mineral mineral) {
        name.setText(mineral.getName());
        name.setEnabled(false);
        textDescription.setText(mineral.getDescription());
        formula.setText(mineral.getGeneralFormula().getFormulaAsString());
        crystalStructure.setSelectedItem(mineral.getCrystalStructure());
        hardness.setText(String.valueOf(mineral.getHardness()));
        density.setText(String.valueOf(mineral.getDensity()));
        ior.setText(String.valueOf(mineral.getIndexOfRefraction()));
        cleavage.setSelectedItem(mineral.getCleavage());
    }

}
