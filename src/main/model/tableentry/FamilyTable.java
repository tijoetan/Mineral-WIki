package model.tableentry;

import model.modelexceptions.DuplicationException;
import model.modelexceptions.FamilyDuplicationException;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Implementation of WikiEntryTable for storing a collection of mineral families

public class FamilyTable implements WikiEntryTable {

    private final HashMap<String, WikiEntry> familyNameTable;

    public FamilyTable() {
        this.familyNameTable = new HashMap<>();
    }

    // getters
    public HashMap<String, WikiEntry> getFamilyNameTable() {
        return this.familyNameTable;
    }

    @Override
    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        WikiEntry requestedFamily = this.familyNameTable.get(name);
        if (requestedFamily != null) {
            return requestedFamily;
        } else {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public void addEntry(WikiEntry entry) throws DuplicationException {
        if (this.familyNameTable.get(entry.getName()) == null) {
            this.familyNameTable.put(entry.getName(), entry);
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

    public List<WikiEntry> getFamilies() {
        return new ArrayList<>(familyNameTable.values());
    }
}

