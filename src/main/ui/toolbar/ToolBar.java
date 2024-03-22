package ui.toolbar;

import model.entries.Mineral;
import model.modelexceptions.DuplicationException;
import ui.CardPanel;
import ui.additionmenu.MineralQueryHandler;
import ui.table.TableDataHandler;
import utils.fieldnames.WindowNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel {
    private CardPanel windows;

    private TableDataHandler mineralTableView;
    private TableDataHandler familyTable;

    private JToolBar toolBar;
    private JTextField searchBox;


    public ToolBar(TableDataHandler mineralTableView, TableDataHandler familyTable,
                   CardPanel panel) {

        this.windows = panel;
        this.mineralTableView = mineralTableView;
        this.familyTable = familyTable;
        setPreferredSize(new Dimension(1280, 30));
        setLayout(new BorderLayout());

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addButtons();

        add(toolBar, BorderLayout.WEST);
    }

    private void addButtons() {
        JButton addMineralButton = new JButton("Add Mineral");
        addMineralButton.addActionListener(new MineralAdditionButtonListener());
        toolBar.add(addMineralButton);
        toolBar.addSeparator();

        JButton tableViewButton = new JButton("Table Page");
        tableViewButton.addActionListener(new TableViewButtonListener());
        toolBar.add(tableViewButton);
        toolBar.add(Box.createHorizontalStrut(750));

        JButton editButton = new JButton("Edit Item");
        editButton.addActionListener(new EditButtonListener());
        toolBar.add(editButton);
        toolBar.add(Box.createHorizontalStrut(50));

        addSearchSection();
    }

    private void addSearchSection() {
        JTextField searchBar = new JTextField(20);
        toolBar.add(searchBar);
        toolBar.addSeparator();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener(searchBar));
        toolBar.add(searchButton);

    }


    protected class SearchButtonListener implements ActionListener {

        JTextField searchQuery;

        public SearchButtonListener(JTextField searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(searchQuery.getText());
        }
    }

    protected class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (windows.getActivePanelName().equals(WindowNames.TABLE_PAGE)) {
                MineralQueryHandler.showErrorMessage("Cannot edit minerals in current view");
            }
        }
    }

    protected class TableViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.showPanel(WindowNames.TABLE_PAGE);
        }
    }

    protected class MineralAdditionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Mineral userMineral = MineralQueryHandler.queryMineral();
            if (userMineral != null) {
                try {
                    mineralTableView.addEntry(userMineral);
                } catch (DuplicationException ex) {
                    MineralQueryHandler.showErrorMessage("Mineral Names cannot be repeated");
                }
            }
        }
    }
}

