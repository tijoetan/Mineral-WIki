package model.tableentry;

import utils.fieldnames.Attributes;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.FamilyDuplicationException;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Implementation of WikiEntryTable for storing a collection of mineral families

public class FamilyTable implements WikiEntryTable {

    private final HashMap<String, WikiEntry> familyNameTable;

    // EFFECTS: constructs the FamilyTable with familyNameTable being a new HashMap
    public FamilyTable() {
        this.familyNameTable = new HashMap<>();
    }

    // getters
    public HashMap<String, WikiEntry> getFamilyNameTable() {
        return this.familyNameTable;
    }

    // EFFECTS: returns Item in familyNameTable where name matches key
    //          throws ItemNotFoundException if no match is found
    @Override
    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        WikiEntry requestedFamily = this.familyNameTable.get(name);
        if (requestedFamily != null) {
            return requestedFamily;
        } else {
            throw new ItemNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given entry to familyNameTable with the key being the name of the family
    //          DuplicateException if item already exists in table
    @Override
    public void addEntry(WikiEntry entry) throws DuplicationException {
        if (this.familyNameTable.get(entry.getName()) == null) {
            this.familyNameTable.put(entry.getName(), entry);
        } else {
            throw new FamilyDuplicationException();
        }

    }

    // MODIFIES: this
    // EFFECTS: Removes entry in familyNameTable with given name
    //          throws ItemNotFoundException if item is not in table
    @Override
    public void removeEntry(String name) throws ItemNotFoundException {
        if (this.familyNameTable.get(name) != null) {
            this.familyNameTable.remove(name);
        } else {
            throw new ItemNotFoundException();
        }
    }

    @Override
    public String[][] getTableAsArray(Attributes attributes) {
        List<WikiEntry> familyValues = new ArrayList<>(familyNameTable.values());
        String[][] familyArray = new String[familyValues.size()][];
        for (int i = 0; i < familyValues.size(); i++) {
            familyArray[i] = familyValues.get(i).giveAttributeAsObjects();
        }
        return familyArray;
    }

    // EFFECTS: creates a JSONObject containing key value pairs of the family names and a JSONObject of the family
    public JSONObject toJson() {
        JSONObject tableJson = new JSONObject();
        for (WikiEntry family : familyNameTable.values()) {
            tableJson.put(family.getName(), family.toJson());
        }
        return tableJson;
    }

    // EFFECTS: returns the values stored in familyNameTable
    public List<WikiEntry> getFamilies() {
        return new ArrayList<>(familyNameTable.values());
    }
}

