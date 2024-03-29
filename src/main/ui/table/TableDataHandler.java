package ui.table;

import model.entries.WikiEntry;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;
import utils.fieldnames.AttributeNames;
import utils.fieldnames.Attributes;

import javax.swing.table.AbstractTableModel;

public class TableDataHandler extends AbstractTableModel {

    private final String[] colNames;
    private String[][] tableValues;

    private final WikiEntryTable table;
    private Attributes sortOrder;

    public TableDataHandler(WikiEntryTable table) {
        if (table instanceof MineralTable) {
            this.colNames = AttributeNames.MINERAL_ATTRIBUTE_NAMES;
        } else if (table instanceof FamilyTable) {
            this.colNames = AttributeNames.FAMILY_ATTRIBUTE_NAMES;
        } else {
            throw new IllegalArgumentException();
        }

        this.table = table;
        sortOrder = Attributes.DEFAULT;
        tableValues = table.getTableAsArray(sortOrder);


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
        try {
            return colNames[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }

    }

    public WikiEntryTable getTable() {
        return table;
    }

    public WikiEntry getEntry(String name) throws ItemNotFoundException {
        return table.getRequestedEntry(name);
    }

    public void updateValues() {
        tableValues = table.getTableAsArray(sortOrder);
        fireTableDataChanged();
    }


    public void addEntry(WikiEntry userMineral) throws DuplicationException {
        table.addEntry(userMineral);
        updateValues();
    }

    public void deleteEntry(String name) throws ItemNotFoundException {
        table.removeEntry(name);
        updateValues();
    }

    public void sortAndUpdate(Attributes sortOrder) {
        if (this.sortOrder != sortOrder) {
            this.sortOrder = sortOrder;
            updateValues();
        } else {
            reverseData();
        }
    }

    public Attributes getSortOrder() {
        return sortOrder;
    }

    public void reverseData() {
        String[][] tempData = new String[tableValues.length][];
        for (int i = 0; i < tableValues.length; i++) {
            tempData[tableValues.length - i - 1] = tableValues[i];
        }
        tableValues = tempData;
        fireTableDataChanged();

    }
}
