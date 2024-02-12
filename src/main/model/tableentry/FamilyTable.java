package model.tableentry;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.enums.EntryType;
import model.exceptions.EmptyTableException;
import model.exceptions.ItemNotFoundException;
import model.exceptions.MineralDuplicateException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FamilyTable implements WikiEntryTable {

    private final HashMap<String, Family> familyNameTable;

    public FamilyTable() {
        this.familyNameTable = new HashMap<>();
    }


    @Override
    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        Family requestedFamily = this.familyNameTable.get(name);
        if (requestedFamily != null) {
            return requestedFamily;
        } else {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public void addEntry(WikiEntry entry) throws MineralDuplicateException {
        if (this.familyNameTable.get(entry.getName()) != null) {
            this.familyNameTable.put(entry.getName(), (Family) entry);
        } else {
            throw new MineralDuplicateException();
        }

    }

    @Override
    public void removeEntry(String name) throws ItemNotFoundException {
        if (this.familyNameTable.get(name) != null) {
            this.familyNameTable.remove(name);
        } else {
            throw new ItemNotFoundException();
        }
    }
}

