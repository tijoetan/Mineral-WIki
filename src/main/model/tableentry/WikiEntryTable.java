package model.tableentry;

import model.entries.WikiEntry;
import model.enums.Attributes;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.ItemNotFoundException;
import org.json.JSONObject;
import persistence.Writable;

// Interface for the table structure

public interface WikiEntryTable extends Writable {
    WikiEntry getRequestedEntry(String name) throws ItemNotFoundException;

    void addEntry(WikiEntry entry) throws DuplicationException;

    void removeEntry(String name) throws ItemNotFoundException;

    String[][] getTableAsArray(Attributes attribute);

    // List<WikiEntry> getAllMatches();


    JSONObject toJson();

}
