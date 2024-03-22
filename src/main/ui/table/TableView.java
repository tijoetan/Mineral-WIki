package ui.table;

import model.enums.Attributes;
import model.tableentry.WikiEntryTable;
import ui.additionmenu.MineralQueryHandler;
import utils.fieldnames.AttributeNames;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableView extends JScrollPane {

    private final JTable viewTable;
    private final TableDataHandler handler;

    public TableView(WikiEntryTable table, Dimension preferredSize) {
        handler = new TableDataHandler(table);
        viewTable = new JTable(handler);
        this.viewTable.setFillsViewportHeight(true);
        this.viewTable.getTableHeader().addMouseListener(new ClickMouseListener());
        setViewportView(this.viewTable);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        setPreferredSize(preferredSize);

    }

    public TableDataHandler getModel() {
        return handler;
    }

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
                MineralQueryHandler.showErrorMessage("Cannot sort this");

        }
    }

    protected class ClickMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickPoint = e.getPoint();
            int column = viewTable.columnAtPoint(clickPoint);
            String clickedColumnName = viewTable.getColumnName(column);
            sortTableBy(clickedColumnName);
        }
    }
}


