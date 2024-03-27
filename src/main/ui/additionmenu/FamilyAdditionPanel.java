package ui.additionmenu;

import javax.swing.*;
import java.awt.*;

public class FamilyAdditionPanel extends JPanel {
    private JTextField familyName;
    private JTextArea familyDescription;
    private JTextField familyFormula;

    GridBagConstraints constraints;

    public FamilyAdditionPanel() {
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

}
