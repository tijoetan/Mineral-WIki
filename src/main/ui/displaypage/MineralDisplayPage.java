package ui.displaypage;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.enums.Cleavage;
import model.modelexceptions.UnknownElementException;
import org.jetbrains.annotations.NotNull;
import utils.Images;
import utils.StringUtils;
import utils.fieldnames.AttributeNames;
import utils.fieldnames.Constants;

import javax.swing.*;
import java.awt.*;

public class MineralDisplayPage extends JPanel {

    private Mineral mineral;

    private JTable stats;
    private JLabel descriptionLabel;

    private JLabel formula;

    private JLabel cleavage;
    private JLabel crystalStructure;


    public MineralDisplayPage(Mineral mineral) {

        this.mineral = mineral;
        setLayout(new BorderLayout());
        setupCenterPane();
        setupSidePane();

    }

    public Mineral getMineral() {
        return this.mineral;
    }


    private void setupCenterPane() {
        JLabel mineralNameLabel = new JLabel(StringUtils.getSentenceCase(mineral.getName()));
        mineralNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mineralNameLabel.setFont(new Font("Inter", Font.ITALIC | Font.BOLD, 70));
        System.out.println(mineralNameLabel.getFont());
        JPanel labelBox = new JPanel(new BorderLayout());
        labelBox.add(mineralNameLabel, BorderLayout.WEST);
        labelBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        labelBox.setPreferredSize(new Dimension(200, 75));

        descriptionLabel = new JLabel("<html>"
                + StringUtils.wrapString(mineral.getDescription(), Constants.MAX_LINE_LENGTH, Constants.WRAP_FOR_GUI)
                + "</html>");
        descriptionLabel.setFont(new Font("Inter", Font.PLAIN, 30));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(labelBox, BorderLayout.NORTH);
        JScrollPane descriptionBox = new JScrollPane(descriptionLabel);
        textPanel.add(descriptionBox, BorderLayout.CENTER);

        add(textPanel, BorderLayout.CENTER);
    }

    private void setupSidePane() {

        JPanel formulaPanel = new JPanel();
        formulaPanel.setLayout(new BoxLayout(formulaPanel, BoxLayout.Y_AXIS));
        formulaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        setupFormulaField(formulaPanel);

        JPanel enumPanel = new JPanel(new BorderLayout());

        JPanel cleavagePanel = setupCleavagePanel();
        JPanel crystalPanel = setupCleavagePanel();



        enumPanel.add(cleavagePanel, BorderLayout.WEST);
        enumPanel.add(crystalPanel, BorderLayout.EAST);


        setupStats();

        setupSidePane(formulaPanel, enumPanel);
    }

    private void setupSidePane(JPanel formulaPanel, JPanel enumPanel) {
        JPanel sidePane = new JPanel();
        sidePane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        sidePane.add(formulaPanel, constraints);
        constraints.gridy++;
        JScrollPane pane = new JScrollPane(stats);
        pane.setPreferredSize(new Dimension(200, 180));
        sidePane.add(pane, constraints);
        constraints.gridy++;
        sidePane.add(enumPanel, constraints);
        add(sidePane, BorderLayout.WEST);
    }

    private JPanel setupEnumPanel(JLabel attributeLabel,
                                  String attributeName,
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


        attributeLabel = new JLabel(StringUtils.getSentenceCase(attributeValue));
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
        return setupEnumPanel(cleavage,
                AttributeNames.CLEAVAGE,
                mineral.getCleavage().getLiteralString(),
                Images.getInstanceCleavageImage(mineral.getCleavage()));
    }

    private void setupStats() {
        stats = new JTable(getQuantitativeTable(),
                new String[]{"Property", "Value"});
        stats.setFillsViewportHeight(true);
        stats.setRowHeight(60);
    }

    private void setupFormulaField(JPanel qualitativePanel) {
        formula = new JLabel(mineral.getGeneralFormula().getFormulaAsString());
        formula.setFont(new Font("Inter", Font.ITALIC, 30));
        formula.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel formulaLabel = new JLabel(AttributeNames.FORMULA + ": ");
        formulaLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        formulaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        formulaLabel.setFont(new Font("Inter", Font.PLAIN, 30));
        formulaLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formulaLabel.getPreferredSize().height));

        qualitativePanel.add(formulaLabel);
        qualitativePanel.add(formula);
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
