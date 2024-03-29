package ui;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import ui.additionmenu.UserQuery;
import ui.additionmenu.familyaddition.FamilyQueryHandler;
import ui.additionmenu.mineraladdition.MineralQueryHandler;
import ui.cardpanel.CardPanel;
import ui.clickeditemhandler.ClickObserver;
import ui.clickeditemhandler.ClickedItemHandler;
import ui.displaypage.ItemView;
import ui.table.TableView;
import ui.toolbar.ToolBar;
import utils.fieldnames.PropertyNames;
import utils.fieldnames.WindowNames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MineralWikiGuiApp implements ClickObserver {

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
        ClickedItemHandler.getInstance().addObserver(this);

        JFrame mainFrame = new JFrame("Mineral Database");
        switchableWindowPanel = new CardPanel();

        setupTableView();
        itemView = new ItemView();
        toolBar = new ToolBar(mineralTableView.getModel(),
                familyTableView.getModel(), switchableWindowPanel);
        setupToolbar();


        addWindows();
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

    private void setupToolbar() {
        toolBar.addPropertyChangeListener(PropertyNames.ITEM_DELETED, e -> deleteSelectedItem());
        toolBar.addPropertyChangeListener(PropertyNames.ITEM_EDITED, e -> editSelectedItem());
    }

    private void addWindows() {
        switchableWindowPanel.add(tableView, WindowNames.TABLE_PAGE);
        switchableWindowPanel.add(itemView, WindowNames.ITEM_PAGE);
    }

    private void editSelectedItem() {
        WikiEntry selectedItem = itemView.getHostedItem();
        if (selectedItem instanceof Mineral) {
            Mineral updatedMineral = MineralQueryHandler.queryEditMineral((Mineral) selectedItem);
            mineralTableView.getModel().updateValues();
            System.out.println(Arrays.toString(updatedMineral.giveAttributeAsObjects()));
            itemView.updateDisplayPage(updatedMineral);
        } else if (selectedItem instanceof Family) {
            Family updatedFamily = FamilyQueryHandler.queryEditFamily((Family) selectedItem, mineralTable);
            familyTableView.getModel().updateValues();
            itemView.updateDisplayPage(updatedFamily);
        }
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
            UserQuery.showErrorMessage("Nothing to delete");
        }


    }

    private void setupTableView() {
        tableView = new JPanel();
        tableView.setLayout(new BorderLayout());

        mineralTableView = new TableView(mineralTable, new Dimension(2 * 1280 / 3, 720));
//        mineralTableView.addPropertyChangeListener(PropertyNames.ITEM_CLICKED, new SwitchWindowOnMineralClick());
        tableView.add(mineralTableView, BorderLayout.WEST);

        familyTableView = new TableView(familyTable, null);
//        familyTableView.addPropertyChangeListener(PropertyNames.ITEM_CLICKED, new SwitchWindowOnMineralClick());
        tableView.add(familyTableView, BorderLayout.CENTER);

    }

    @Override
    public void update() {
        itemView.updateDisplayPage(ClickedItemHandler.getInstance().getClickedItem());
        System.out.println(Arrays.toString(ClickedItemHandler.getInstance().getClickedItem().giveAttributeAsObjects()));
        switchableWindowPanel.showPanel(WindowNames.ITEM_PAGE);
    }

}
