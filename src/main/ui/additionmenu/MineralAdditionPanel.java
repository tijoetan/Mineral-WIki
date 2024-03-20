package ui.additionmenu;

import model.enums.Cleavage;
import model.enums.CrystalStructure;
import utils.fieldnames.AttributeNames;

import javax.swing.*;

public class MineralAdditionPanel {


    private JTextField name;
    private JTextArea description; // description
    private JComboBox crystalStructure;
    private JComboBox cleavage;
    private JTextField hardness;
    private JTextField ior;
    private JTextField density;
    private JTextField formula;

    JPanel hostPanel;

    public MineralAdditionPanel() {
        resetFields();
        addFieldsToPanel();
    }

    public String getName() {
        return name.getText().trim();
    }

    public String getDescription() {
        return description.getText().trim();
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

    private void resetFields() {
        hostPanel = new JPanel();
        hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.Y_AXIS));
        hostPanel.setBounds(100, 100, 400, 200);

        name = new JTextField(3);
        description = new JTextArea(6, 6);
        crystalStructure = new JComboBox(CrystalStructure.values());
        cleavage = new JComboBox(Cleavage.values());
        hardness = new JTextField(3);
        ior = new JTextField(3);
        density = new JTextField(3);
        formula = new JTextField(8);
    }

    private void addFieldsToPanel() {
        for (Object[] pair : produceLabelComponentPairs()) {
            hostPanel.add(new JLabel((String) pair[0]));
            hostPanel.add((JComponent) pair[1]);
            hostPanel.add(Box.createVerticalStrut(5));
        }
    }

    public Object[][] produceLabelComponentPairs() {
        return new Object[][]{new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[0], name},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[1], crystalStructure},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[2], formula},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[3], hardness},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[4], density},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[5], ior},
                new Object[]{AttributeNames.MINERAL_ATTRIBUTE_NAMES[6], cleavage},
                new Object[]{"Description", description}};

    }

}
