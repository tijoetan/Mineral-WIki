package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import model.entries.Mineral;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import sun.awt.image.MultiResolutionCachedImage;
import sun.awt.image.ToolkitImage;
import ui.additionmenu.MineralQueryHandler;
import ui.displaypage.ItemView;
import ui.table.TableView;
import ui.toolbar.ToolBar;
import utils.fieldnames.PropertyNames;
import utils.fieldnames.WindowNames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MineralWikiGuiApp {

    private final JFrame mainFrame;
    private final CardPanel switchableWindowPanel;

    private final MineralTable mineralTable;
    private final FamilyTable familyTable;

    private JPanel tableView;
    private final ItemView itemView;

    private TableView mineralTableView;
    private TableView familyTableView;

    private final ToolBar toolBar;

    public MineralWikiGuiApp() {

        mineralTable = new MineralTable();
        familyTable = new FamilyTable();

        mainFrame = new JFrame("Mineral Database");
        switchableWindowPanel = new CardPanel();

        setupTableView();
        itemView = new ItemView();
        toolBar = new ToolBar(mineralTableView.getModel(),
                familyTableView.getModel(), switchableWindowPanel);
        toolBar.addPropertyChangeListener(PropertyNames.ITEM_DELETED, e -> deleteSelectedItem());
        toolBar.addPropertyChangeListener(PropertyNames.ITEM_EDITED, e -> editSelectedItem());


        switchableWindowPanel.add(tableView, WindowNames.TABLE_PAGE);
        switchableWindowPanel.add(itemView, WindowNames.ITEM_PAGE);
        switchableWindowPanel.showPanel(WindowNames.TABLE_PAGE);

        mainFrame.add(toolBar, BorderLayout.NORTH);
        mainFrame.add(switchableWindowPanel, BorderLayout.CENTER);
        mainFrame.setSize(1280, 720);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
        try {
            mainFrame.setIconImage(ImageIO.read(new File("data/icons/appIcon.png")));
        } catch (IOException e) {
            //
        }
    }

    private void editSelectedItem() {
        Mineral updatedMineral = MineralQueryHandler.queryEditMineral((Mineral) itemView.getHostedItem());
        mineralTableView.getModel().updateValues();
        System.out.println(Arrays.toString(updatedMineral.giveAttributeAsObjects()));
        itemView.updateDisplayPage(updatedMineral);
    }

    private void deleteSelectedItem() {
        try {
            mineralTableView.getModel().deleteEntry(itemView.getHostedItemName());
            itemView.setupDefaultView();
            return;
        } catch (ItemNotFoundException ignored) {
            // Not in mineral table
        }

        try {
            familyTableView.getModel().deleteEntry(itemView.getHostedItemName());
        } catch (ItemNotFoundException ignored) {
            MineralQueryHandler.showErrorMessage("Nothing to delete");
        }


    }

    private void setupTableView() {
        tableView = new JPanel();
        tableView.setLayout(new BorderLayout());

        mineralTableView = new TableView(mineralTable, new Dimension(2 * 1280 / 3, 720));
        mineralTableView.addPropertyChangeListener(PropertyNames.ITEM_CLICKED, new SwitchWindowOnMineralClick());
        tableView.add(mineralTableView, BorderLayout.WEST);

        familyTableView = new TableView(familyTable, null);
        tableView.add(familyTableView, BorderLayout.CENTER);

    }

    protected class SwitchWindowOnMineralClick implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            itemView.updateDisplayPage((Mineral) mineralTableView.getClickedItem());
            switchableWindowPanel.showPanel(WindowNames.ITEM_PAGE);
        }
    }

}
