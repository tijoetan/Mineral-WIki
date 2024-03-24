package ui.displaypage;

import javafx.scene.shape.Line;
import model.entries.Mineral;
import model.enums.Attributes;
import utils.StringUtils;
import utils.fieldnames.AttributeNames;
import utils.fieldnames.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.security.acl.Group;

public class MineralDisplayPage extends JPanel {

    private Mineral mineral;

    private JTable stats;
    private JLabel descriptionLabel;

    public MineralDisplayPage(Mineral mineral) {

        this.mineral = mineral;
        setLayout(new BorderLayout());
        setupCenterPane();
        setupStats();

    }


    private void setupCenterPane() {
        JLabel mineralNameLabel = new JLabel(StringUtils.getSentenceCase(mineral.getName()));
        mineralNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mineralNameLabel.setFont(new Font("Inter", Font.ITALIC, 70));
        JPanel labelBox = new JPanel(new BorderLayout());
        labelBox.add(mineralNameLabel, BorderLayout.WEST);
        labelBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        labelBox.setPreferredSize(new Dimension(200, 60));

        descriptionLabel = new JLabel("<html>"
                + StringUtils.wrapString(mineral.getDescription(), Constants.MAX_LINE_LENGTH, Constants.WRAP_FOR_GUI)
                + "</html>");
        descriptionLabel.setFont(new Font("Garamond", Font.PLAIN, 30));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(labelBox, BorderLayout.NORTH);
        JScrollPane descriptionBox = new JScrollPane(descriptionLabel);
        textPanel.add(descriptionBox, BorderLayout.CENTER);

        add(textPanel, BorderLayout.CENTER);
    }

    private void setupStats() {
        stats = new JTable(getQuantitativeTable(),
                new String[]{"Property", "Value"});
        stats.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(stats);
        pane.setPreferredSize(new Dimension(100, 100));
        add(new JScrollPane(stats), BorderLayout.WEST);
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

    public static void main(String[] args) {
        Mineral testMineral = new Mineral("Mineral");
        testMineral.setHardness(20.0f);
        testMineral.setDensity(10.0f);
        testMineral.setIndexOfRefraction(4.0f);
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
