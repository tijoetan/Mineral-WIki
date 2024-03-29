package ui.table;

import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.WikiEntryTable;
import ui.clickeditemhandler.ClickedItemHandler;
import ui.misc.UserQuery;
import utils.fieldnames.AttributeNames;
import utils.fieldnames.Attributes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class TableView extends JScrollPane {

    private final JTable viewTable;
    private final TableDataHandler handler;

    // EFFECTS: constructs tableView based on given table
    public TableView(WikiEntryTable table, Dimension preferredSize) {
        handler = new TableDataHandler(table);
        viewTable = new JTable(handler);
        //this.viewTable.setFillsViewportHeight(true);
        this.viewTable.getTableHeader().addMouseListener(new ClickMouseListener());
        setViewportView(this.viewTable);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setPreferredSize(preferredSize);
        viewTable.getColumn(AttributeNames.NAME).setCellRenderer(new NameCellRenderer());
        viewTable.addMouseMotionListener(new HyperlinkManager());
        viewTable.addMouseListener(new HyperlinkManager());
        viewTable.setRowHeight(30);
    }

    // getters
    public TableDataHandler getModel() {
        return handler;
    }

    // MODIFIES: this, handler
    // EFFECTS: sorts table data from columnName
    public void sortTableBy(String columnName) {

        switch (columnName) {
            case AttributeNames.CRYSTAL_STRUCTURE: handler.sortAndUpdate(Attributes.CRYSTAL);
                break;
            case AttributeNames.CLEAVAGE: handler.sortAndUpdate(Attributes.CLEAVAGE);
                break;
            case AttributeNames.HARDNESS: handler.sortAndUpdate(Attributes.HARDNESS);
                break;
            case AttributeNames.IOR: handler.sortAndUpdate(Attributes.IOR);
                break;
            case AttributeNames.DENSITY: handler.sortAndUpdate(Attributes.DENSITY);
                break;
            case AttributeNames.NAME: handler.sortAndUpdate(Attributes.DEFAULT);
                break;
            default:
                UserQuery.showErrorMessage("Cannot sort this");

        }
    }

    // listener class for table view sorting
    protected class ClickMouseListener extends MouseAdapter {

        // MODIFIES: this
        // EFFECTS: sorts Table based on clicked column
        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickPoint = e.getPoint();
            int column = viewTable.columnAtPoint(clickPoint);
            String clickedColumnName = viewTable.getColumnName(column);
            sortTableBy(clickedColumnName);
        }

    }

    // listener class for clicking link
    protected class HyperlinkManager extends MouseAdapter {

        // MODIFIES: this
        // EFFECTS: change cursor if hovering over name column
        @Override
        public void mouseMoved(MouseEvent e) {
            Point clickPoint = e.getPoint();
            if (viewTable.getColumnName(viewTable.columnAtPoint(clickPoint)).equals(AttributeNames.NAME)) {
                viewTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return;
            }
            viewTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        // MODIFIES: ClickItemHandler
        // EFFECTS: changes clickedItem to item at click location
        @Override
        public void mousePressed(MouseEvent e) {
            Point clickPoint = e.getPoint();
            System.out.println("Click");
            int column = viewTable.columnAtPoint(clickPoint);
            if (viewTable.getColumnName(column).equals(AttributeNames.NAME)) {
                int row = viewTable.rowAtPoint(clickPoint);
                String nameAtPoint = (String) viewTable.getValueAt(row, column);
                try {
                    WikiEntry clickedItem = handler.getEntry(nameAtPoint);
                    System.out.println(Arrays.toString(clickedItem.giveAttributeAsObjects()));
                    ClickedItemHandler.getInstance().setClickedItem(clickedItem);
                    /* firePropertyChange(PropertyNames.ITEM_CLICKED, true, false); */
                } catch (ItemNotFoundException ex) {
                    throw new IllegalStateException();
                }

            }
        }


    }

}
