package ui.table;

import model.tableentry.WikiEntryTable;
import ui.table.TableDataHandler;

import javax.swing.*;

public class TableView {
    JTable table;

    public TableView(WikiEntryTable table) {
        this.table = new JTable(new TableDataHandler(table));
    }
}
