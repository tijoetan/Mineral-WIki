package ui.displaypage;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.UnknownElementException;
import org.jetbrains.annotations.NotNull;
import utils.Images;
import utils.StringUtils;
import utils.fieldnames.AttributeNames;

import javax.swing.*;
import java.awt.*;

public class MineralDisplayPage extends DisplayPage {

    private final Mineral mineral;
    private JTable stats;
    private JPanel enumPanel;


    public MineralDisplayPage(Mineral mineral) {
        super(mineral);
        this.mineral = mineral;
        setupSidePanel();
    }

    public Mineral getMineral() {
        return this.mineral;
    }

    @Override
    public void setupSidePanel() {

        setupFormulaField();

        enumPanel = new JPanel(new BorderLayout());

        JPanel cleavagePanel = setupCleavagePanel();
        JPanel crystalPanel = setupCrystalPanel();


        enumPanel.add(cleavagePanel, BorderLayout.WEST);
        enumPanel.add(crystalPanel, BorderLayout.EAST);


        setupStats();

        placeItemsOnPanel();
    }

    private void placeItemsOnPanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        sidePanel.add(formulaPanel, constraints);
        constraints.gridy++;
        JScrollPane pane = new JScrollPane(stats);
        pane.setPreferredSize(new Dimension(200, 180));
        sidePanel.add(pane, constraints);
        constraints.gridy++;
        sidePanel.add(enumPanel, constraints);
        add(sidePanel, BorderLayout.WEST);
    }

    private JPanel setupEnumPanel(String attributeName,
                                  String attributeValue,
                                  ImageIcon displayImage) {
        JPanel enumPanel = new JPanel();
        enumPanel.setLayout(new BoxLayout(enumPanel, BoxLayout.Y_AXIS));
        enumPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        JLabel cleavageLabel = new JLabel(attributeName);
        cleavageLabel.setFont(new Font("Inter", Font.PLAIN, 25));
        cleavageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cleavageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cleavageLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));


        JLabel attributeLabel = new JLabel(StringUtils.getSentenceCase(attributeValue));
        attributeLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        attributeLabel.setFont(new Font("Inter", Font.ITALIC, 40));
        attributeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enumPanel.add(cleavageLabel);
        enumPanel.add(attributeLabel);
        JLabel imageLabel = new JLabel(displayImage);
        enumPanel.add(imageLabel);
        return enumPanel;
    }

    @NotNull
    private JPanel setupCleavagePanel() {
        return setupEnumPanel(
                AttributeNames.CLEAVAGE,
                mineral.getCleavage().getLiteralString(),
                Images.getInstanceCleavageImage(mineral.getCleavage()));
    }

    private JPanel setupCrystalPanel() {
        return setupEnumPanel(
                AttributeNames.CRYSTAL_STRUCTURE,
                mineral.getCrystalStructure().getLiteralString(),
                Images.getInstanceCrystalImage(mineral.getCrystalStructure()));
    }

    private void setupStats() {
        stats = new JTable(getQuantitativeTable(),
                new String[]{"Property", "Value"});
        stats.setFillsViewportHeight(true);
        stats.setRowHeight(60);
    }

    public String[][] getQuantitativeTable() {
        String[] ior = new String[]{AttributeNames.IOR + " (Dimensionless)",
                String.valueOf(mineral.getIndexOfRefraction())};
        String[] hardness = new String[]{AttributeNames.HARDNESS + " (Mohs)",
                String.valueOf(mineral.getHardness())};
        String[] density = new String[]{AttributeNames.DENSITY + " (g/cm^3)",
                String.valueOf(mineral.getDensity())};

        return new String[][]{hardness, ior, density};
    }

    public static void main(String[] args) throws UnknownElementException {
        Mineral testMineral = new Mineral("Mineral");
        testMineral.setHardness(20.0f);
        testMineral.setDensity(10.0f);
        testMineral.setIndexOfRefraction(4.0f);
        testMineral.setCrystalStructure(CrystalStructure.CUBIC);
        testMineral.setGeneralFormula(new Formula("(Pb, S, F, O)1290Cl2"));
        testMineral.setCleavage(Cleavage.BASAL);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            builder.append("The quick brown fox jumps over the lazy dog");
        }
        testMineral.setDescription(builder.toString());

        JFrame frame = new JFrame();
        frame.add(new MineralDisplayPage(testMineral));
        frame.setSize(1280, 720);
        frame.setVisible(true);
    }

}
