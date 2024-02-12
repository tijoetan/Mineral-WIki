package model.tableentry;

import model.entries.WikiEntry;
import model.exceptions.ItemNotFoundException;
import model.exceptions.MineralDuplicateException;

public interface WikiEntryTable {
    WikiEntry getRequestedEntry(String name) throws ItemNotFoundException;

    void addEntry(WikiEntry entry) throws MineralDuplicateException;

    void removeEntry(String name) throws ItemNotFoundException;

}
