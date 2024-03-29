package ui.toolbar;

import model.entries.Family;
import model.entries.Mineral;
import model.modelexceptions.DuplicationException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import persistence.InvalidFileException;
import persistence.TableReader;
import persistence.TableWriter;
import ui.misc.CardPanel;
import ui.additionmenu.QuerySelector;
import ui.additionmenu.familyaddition.FamilyQueryHandler;
import ui.additionmenu.mineraladdition.MineralQueryHandler;
import ui.filebrowser.LoadSavePopupMenu;
import ui.table.TableDataHandler;
import ui.misc.UserQuery;
import utils.fieldnames.PropertyNames;
import utils.fieldnames.WindowNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Toolbar with important buttons

public class ToolBar extends JPanel {

    private String savePath;

    private LoadSavePopupMenu menu;
    private final CardPanel windows;

    private final TableDataHandler mineralTableView;
    private final TableDataHandler familyTableView;

    private final JToolBar toolBar;
    private JTextField searchBox;

    private JButton searchButton;
    private JButton addMineralButton;
    private JButton tableViewButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton itemViewButton;

    // EFFECTS: constructs new Toolbar
    public ToolBar(TableDataHandler mineralTableView, TableDataHandler familyTableView,
                   CardPanel panel) {
        savePath = null;
        this.windows = panel;
        windows.addPropertyChangeListener(PropertyNames.WINDOW_CHANGE_EVENT, new WindowStateListener());
        this.mineralTableView = mineralTableView;
        this.familyTableView = familyTableView;
        setPreferredSize(new Dimension(1280, 30));
        setLayout(new BorderLayout());

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addButtons();

        add(toolBar, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: adds all buttons and spacing to ToolBar
    private void addButtons() {
        addFileButton();
        toolBar.add(Box.createHorizontalStrut(50));
        addTableViewButton();
        toolBar.addSeparator();
        addItemViewButton();
        toolBar.add(Box.createHorizontalStrut(850));
        addAddButton();
        toolBar.addSeparator();
        addEditButton();
        toolBar.addSeparator();
        addDeleteButton();
        toolBar.add(Box.createHorizontalStrut(60));
        //addSearchSection();
    }

    // MODIFIES: this
    // EFFECTS: configures and adds file button to Toolbar
    private void addFileButton() {
        menu = new LoadSavePopupMenu("File");
        menu.addPropertyChangeListener(new FileHandler());
        toolBar.add(menu);
    }

    // MODIFIES: this
    // EFFECTS: configures and adds Item view button to ToolBar
    private void addItemViewButton() {
        itemViewButton = new JButton("Item View");
        itemViewButton.addActionListener(e -> windows.showPanel(WindowNames.ITEM_PAGE));
        toolBar.add(itemViewButton);
    }

    // MODIFES: this
    // EFFECTS: configures and adds Add button to Toolbar
    private void addAddButton() {
        addMineralButton = new JButton("Add Item");
        addMineralButton.addActionListener(new MineralAdditionButtonListener());
        toolBar.add(addMineralButton);
    }

    // MODIFIES: this
    // EFFECTS: configures and adds Table view button to toolbar
    private void addTableViewButton() {
        tableViewButton = new JButton("Table Page");
        tableViewButton.addActionListener(e -> windows.showPanel(WindowNames.TABLE_PAGE));
        toolBar.add(tableViewButton);
    }

    // MODIFIES: this
    // EFFECTS: configures and adds Edit button to toolbar
    private void addEditButton() {
        editButton = new JButton("Edit Item");
        editButton.addActionListener(e -> firePropertyChange(PropertyNames.ITEM_EDITED, true, false));
        editButton.setEnabled(false);
        toolBar.add(editButton);
    }

    // MODIFIES: this
    // EFEFCTS: configures and adds delete button to toolbar
    private void addDeleteButton() {
        deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(new DeleteButtonListener());
        toolBar.add(deleteButton);
    }

    // Not used
    private void addSearchSection() {
        searchBox = new JTextField(20);
        toolBar.add(searchBox);
        toolBar.addSeparator();

        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        toolBar.add(searchButton);

    }

    // MODIFIES: this, mineralTableView
    // EFFECTS: adds user configured mineral to mineralTableView
    private void addMineral() {
        Mineral userMineral = MineralQueryHandler.queryAddMineral();
        if (userMineral != null) {
            try {
                mineralTableView.addEntry(userMineral);
            } catch (DuplicationException ex) {
                UserQuery.showErrorMessage("Mineral Names cannot be repeated");
            }
        }
    }

    // MODIFIES: this, familyTableView
    // EFFECTS: adds user configured family to familyTableView
    public void addFamily() {
        Family userFamily = FamilyQueryHandler.queryAddFamily(mineralTableView.getTable());
        if (userFamily != null) {
            try {
                familyTableView.addEntry(userFamily);
            } catch (DuplicationException e) {
                UserQuery.showErrorMessage("Family Names cannot be repeated");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets save path to that in menu and saves table to the path
    private void saveTableAs() {
        savePath = menu.getSavePath();
        if (!savePath.endsWith(".json")) {
            savePath += ".json";
        }
        saveTable();
    }

    // MODIFIES: this, mineralTableView, familyTableView
    // EFFECTS: configures table views to the file in menu.getLoadPath()
    private void loadTable() {
        TableReader reader = new TableReader(menu.getLoadPath(),
                (FamilyTable) familyTableView.getTable(),
                (MineralTable) mineralTableView.getTable());
        try {
            reader.setupTables();
            mineralTableView.updateValues();
            familyTableView.updateValues();

        } catch (IOException | InvalidFileException e) {
            UserQuery.showErrorMessage("File Error");
        }
        savePath = menu.getLoadPath();
        System.out.println("Loading" + menu.getLoadPath());
    }

    // EFFECTS: writes tables to .json file in savePath
    private void saveTable() {
        if (savePath == null) {
            UserQuery.showErrorMessage("Must choose destination");
            return;
        }

        System.out.println(savePath);
        TableWriter writer = new TableWriter(savePath);
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            UserQuery.showErrorMessage("Could not save file");
        }
        writer.writeToDestination(mineralTableView.getTable(), familyTableView.getTable());
        writer.close();
    }

    // Listener class for File Button clicks
    protected class FileHandler implements PropertyChangeListener {

        // MODIFIES: this
        // EFFECTS: calls correct method depending on button clicked
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(PropertyNames.LOAD_BUTTON_CLICKED)) {
                loadTable();
            } else if (evt.getPropertyName().equals(PropertyNames.SAVE_AS_BUTTON_CLICKED)) {
                saveTableAs();
            } else if (evt.getPropertyName().equals(PropertyNames.SAVE_BUTTON_CLICKED)) {
                saveTable();
            }
        }

    }

    // Not used
    protected class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(searchBox.getText());
        }
    }

    // listener class for addition button
    protected class MineralAdditionButtonListener implements ActionListener {

        // EFFECTS: prompts user on type of entry to add and calls respective method to add it
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (QuerySelector.chooseOption()) {
                case FAMILY:
                    addFamily();
                    System.out.println("Family");
                    break;
                case MINERAL:
                    addMineral();
                    System.out.println("Mineral");
                    break;
            }

        }
    }


    protected class WindowStateListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            boolean enabled = !windows.getActivePanelName().equals(WindowNames.TABLE_PAGE);
            System.out.println(evt.getPropertyName());
            System.out.println("Something has changed");

            editButton.setEnabled(enabled);
            deleteButton.setEnabled(enabled);
            tableViewButton.setEnabled(enabled);

            itemViewButton.setEnabled(!enabled);
            addMineralButton.setEnabled(!enabled);

        }
    }

    protected class DeleteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this item?",
                    "Mineral Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Deleting");
                firePropertyChange(PropertyNames.ITEM_DELETED, true, false);
                windows.showPanel(WindowNames.TABLE_PAGE);
            }
        }
    }
}



