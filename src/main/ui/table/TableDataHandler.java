package ui.table;

import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;
import utils.fieldnames.AttributeNames;

import javax.swing.table.AbstractTableModel;

public class TableDataHandler extends AbstractTableModel {

    private final String[] colNames;
    private final String[][] tableValues;

    public TableDataHandler(WikiEntryTable table) {
        if (table instanceof MineralTable) {
            this.colNames = AttributeNames.MINERAL_ATTRIBUTE_NAMES;
        } else if (table instanceof FamilyTable) {
            this.colNames = AttributeNames.FAMILY_ATTRIBUTE_NAMES;
        } else {
            throw new IllegalArgumentException();
        }

        tableValues = table.getTableAsArray();

    }

    @Override
    public int getRowCount() {
        return tableValues.length;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableValues[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int i) {
        return colNames[i];

    }
}
