package ui.displaypage;

import model.entries.Mineral;

import javax.swing.*;
import java.awt.*;

public class ItemView extends JPanel {
    MineralDisplayPage page;

    public ItemView() {
        page = null;
        setLayout(new BorderLayout());
        setupDefaultView();
    }

    public void setupDefaultView() {
        removeAll();
        JLabel defaultLabel = new JLabel("No Mineral Selected", SwingConstants.CENTER);
        defaultLabel.setFont(new Font("Inter", Font.PLAIN, 60));
        add(defaultLabel, BorderLayout.CENTER);
    }

    public void updateDisplayPage(Mineral mineral) {
        removeAll();
        page = new MineralDisplayPage(mineral);
        add(page, BorderLayout.CENTER);
    }


    public String getHostedItemName() {
        if (page != null) {
            return page.getMineralName();
        }
        return "";
    }
}
