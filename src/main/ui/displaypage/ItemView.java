package ui.displaypage;

import model.entries.Mineral;
import model.entries.WikiEntry;

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

    public void updateDisplayPage(WikiEntry mineral) {
        removeAll();
        page = new MineralDisplayPage((Mineral) mineral);
        System.out.println("Updating");
        add(page, BorderLayout.CENTER);
        updateUI();
    }


    public String getHostedItemName() {
        if (page != null) {
            return getHostedItem().getName();
        }
        return "";
    }

    public WikiEntry getHostedItem() {
        return page.getMineral();
    }
}
