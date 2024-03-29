package ui.displaypage;

import model.entries.WikiEntry;
import utils.StringUtils;
import utils.fieldnames.AttributeNames;
import utils.fieldnames.Constants;

import javax.swing.*;
import java.awt.*;

// Parent class for Mineral/Family Display page

public abstract class DisplayPage extends JPanel {
    protected JPanel centerPanel;

    protected WikiEntry entry;

    protected JPanel sidePanel;

    protected JPanel formulaPanel;

    public WikiEntry getEntry() {
        return entry;
    }

    // EFFECTS: Constructs Display Page
    protected DisplayPage(WikiEntry entry) {
        this.entry = entry;
        setLayout(new BorderLayout());
        setupCenterPane();

    }

    // EFFECTS: sets up Center page with mineral name and description
    protected void setupCenterPane() {
        JLabel itemNameLabel = new JLabel(StringUtils.getSentenceCase(entry.getName()));
        itemNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        itemNameLabel.setFont(new Font("Inter", Font.ITALIC | Font.BOLD, 70));
//        System.out.println(itemNameLabel.getFont());
        JPanel labelBox = new JPanel(new BorderLayout());
        labelBox.add(itemNameLabel, BorderLayout.WEST);
        labelBox.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        labelBox.setPreferredSize(new Dimension(200, 75));

        JLabel descriptionLabel = new JLabel("<html>"
                + StringUtils.wrapString(entry.getDescription(), Constants.MAX_LINE_LENGTH, Constants.WRAP_FOR_GUI)
                + "</html>");
        descriptionLabel.setFont(new Font("Inter", Font.PLAIN, 30));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(labelBox, BorderLayout.NORTH);
        JScrollPane descriptionBox = new JScrollPane(descriptionLabel);
        centerPanel.add(descriptionBox, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    // EFFECTS: sets up formula panel
    protected void setupFormulaField() {
        formulaPanel = new JPanel();
        formulaPanel.setLayout(new BoxLayout(formulaPanel, BoxLayout.Y_AXIS));
        JLabel formula = new JLabel(entry.getGeneralFormula().getFormulaAsString());
        formula.setFont(new Font("Inter", Font.ITALIC, 30));
        formula.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel formulaLabel = new JLabel(AttributeNames.FORMULA + ": ");
        formulaLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        formulaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        formulaLabel.setFont(new Font("Inter", Font.PLAIN, 30));
        formulaLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, formulaLabel.getPreferredSize().height));

        formulaPanel.add(formulaLabel);
        formulaPanel.add(formula);
    }

    protected abstract void setupSidePanel();
}
