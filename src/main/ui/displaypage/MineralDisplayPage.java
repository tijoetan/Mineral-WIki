package ui.displaypage;

import model.entries.Mineral;

import javax.swing.*;

public class MineralDisplayPage extends JPanel {

    private JTable stats;
    private JLabel descriptionLabel;

    public MineralDisplayPage(Mineral mineral) {
        descriptionLabel = new JLabel(mineral.getDescription());

    }

}
