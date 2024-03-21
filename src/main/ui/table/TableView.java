package ui.table;

import model.tableentry.WikiEntryTable;

import javax.swing.*;
import java.awt.*;

public class TableView extends JScrollPane {

    private final JTable viewTable;

    public TableView(WikiEntryTable table, Dimension preferredSize) {
        viewTable = new JTable(new TableDataHandler(table));
        this.viewTable.setFillsViewportHeight(true);
        setViewportView(this.viewTable);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        setPreferredSize(preferredSize);

    }

    public TableDataHandler getModel() {
        return (TableDataHandler) viewTable.getModel();
    }
}
