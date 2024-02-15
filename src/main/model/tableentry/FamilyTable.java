package model.tableentry;

import model.exceptions.DuplicationException;
import model.exceptions.FamilyDuplicationException;
import model.entries.WikiEntry;
import model.exceptions.ItemNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FamilyTable implements WikiEntryTable {

    private final HashMap<String, WikiEntry> familyNameTable;

    public FamilyTable() {
        this.familyNameTable = new HashMap<>();
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

