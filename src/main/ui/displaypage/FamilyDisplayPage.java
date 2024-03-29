package ui.displaypage;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.modelexceptions.UnknownElementException;
import ui.additionmenu.familyaddition.DescendantMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FamilyDisplayPage extends DisplayPage {
    private final Family family;
    private JPanel descendantListPanel;

    protected FamilyDisplayPage(Family family) {
        super(family);
        this.family = family;
        setupSidePanel();
    }

    @Override
    protected void setupSidePanel() {
        sidePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        setupFormulaField();
        sidePanel.add(formulaPanel, constraints);
        sidePanel.setPreferredSize(new Dimension(400, Integer.MAX_VALUE));
        setupDescendantLists();
        constraints.gridy++;
        sidePanel.add(descendantListPanel, constraints);
        add(sidePanel, BorderLayout.WEST);


    }

    private void setupDescendantLists() {
        descendantListPanel = new JPanel(new BorderLayout());
        DescendantMenu menu = new DescendantMenu(3, family.getMineralsWithFamily());
        descendantListPanel.add(menu, BorderLayout.CENTER);
    }


    public static void main(String[] args) throws UnknownElementException {
        Family testFamily = new Family("Mineral");
        testFamily.setGeneralFormula(new Formula("AlSO4"));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            builder.append("The quick brown fox jumps over the lazy dog");
        }
        testFamily.setDescription(builder.toString());
        testFamily.addMineralsWithFamily(Arrays.asList(new Mineral("ef"), new Mineral("fiejfe"),
                new Mineral("fiejaef"), new Mineral("EIQ"), new Mineral("WIQ")));
        JFrame frame = new JFrame();
        frame.add(new FamilyDisplayPage(testFamily));
        frame.setSize(1280, 720);
        frame.setVisible(true);
    }
}
