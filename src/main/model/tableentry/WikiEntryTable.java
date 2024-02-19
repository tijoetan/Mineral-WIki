package model.tableentry;

import model.modelexceptions.DuplicationException;
import model.modelexceptions.ItemNotFoundException;
import model.entries.WikiEntry;

public interface WikiEntryTable {
    WikiEntry getRequestedEntry(String name) throws ItemNotFoundException;

    void addEntry(WikiEntry entry) throws DuplicationException;

    void removeEntry(String name) throws ItemNotFoundException;


}
