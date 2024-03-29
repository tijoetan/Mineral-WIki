package ui.displaypage;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;

import javax.swing.*;
import java.awt.*;

public class ItemView extends JPanel {
    DisplayPage page;

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

    public void updateDisplayPage(WikiEntry entry) {
        removeAll();
        if (entry instanceof Mineral) {
            page = new MineralDisplayPage((Mineral) entry);
        } else if (entry instanceof Family) {
            page = new FamilyDisplayPage((Family) entry);
        }
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
        return page.getEntry();
    }
}
