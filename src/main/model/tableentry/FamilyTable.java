package model.tableentry;

import exceptions.DuplicationException;
import exceptions.FamilyDuplicationException;
import model.entries.Family;
import model.entries.WikiEntry;
import exceptions.ItemNotFoundException;

import java.util.HashMap;

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
    public void addEntry(WikiEntry entry) throws DuplicationException {
        if (this.familyNameTable.get(entry.getName()) != null) {
            this.familyNameTable.put(entry.getName(), (Family) entry);
        } else {
            throw new FamilyDuplicationException();
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

