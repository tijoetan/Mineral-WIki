package ui;

import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import persistence.InvalidFileException;
import persistence.TableReader;
import ui.table.TableView;
import ui.toolbar.ToolBar;
import utils.fieldnames.WindowNames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MineralWikiGuiApp {

    private final JFrame mainFrame;
    private final CardPanel switchableWindowPanel;

    private final MineralTable mineralTable;
    private final FamilyTable familyTable;

    private JPanel tableView;

    private TableView mineralTableView;
    private TableView familyTableView;

    private final ToolBar toolBar;

    public MineralWikiGuiApp() {

        mineralTable = new MineralTable();
        familyTable = new FamilyTable();

        mainFrame = new JFrame("Mineral Database");
        switchableWindowPanel = new CardPanel();

        setupTableView();
        toolBar = new ToolBar(mineralTableView.getModel(),
                familyTableView.getModel(), switchableWindowPanel);

        switchableWindowPanel.add(tableView, WindowNames.TABLE_PAGE);
        switchableWindowPanel.add(new JPanel(), WindowNames.ITEM_PAGE);
        switchableWindowPanel.showPanel(WindowNames.TABLE_PAGE);

        mainFrame.add(toolBar, BorderLayout.NORTH);
        mainFrame.add(switchableWindowPanel, BorderLayout.CENTER);
        mainFrame.setSize(1280, 720);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setLayout(null);
    }

    private void setupTableView() {
        tableView = new JPanel();
        tableView.setLayout(new BorderLayout());

        mineralTableView = new TableView(mineralTable, new Dimension(2 * 1280 / 3, 720));
        tableView.add(mineralTableView, BorderLayout.WEST);

        familyTableView = new TableView(familyTable, null);
        tableView.add(familyTableView, BorderLayout.CENTER);

    }

}
