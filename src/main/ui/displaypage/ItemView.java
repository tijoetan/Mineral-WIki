package ui.displaypage;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;

import javax.swing.*;
import java.awt.*;

// Host container for Mineral/Family display pages

public class ItemView extends JPanel {
    DisplayPage page;

    // EFFECTS: constructs blank ItemView
    public ItemView() {
        page = null;
        setLayout(new BorderLayout());
        setupDefaultView();
    }

    //getters
    public String getHostedItemName() {
        if (page != null) {
            return getHostedItem().getName();
        }
        return "";
    }

    public WikiEntry getHostedItem() {
        return page.getEntry();
    }

    // MODIFIES: this
    // EFFECTS: sets up empty panel
    public void setupDefaultView() {
        removeAll();
        JLabel defaultLabel = new JLabel("No Mineral Selected", SwingConstants.CENTER);
        defaultLabel.setFont(new Font("Inter", Font.PLAIN, 60));
        add(defaultLabel, BorderLayout.CENTER);
    }
    // MODIFIES: this
    // EFFECTS: displays proper display page for given entry

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


}
