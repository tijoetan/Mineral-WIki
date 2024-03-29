package model.tableentry;

import model.modelexceptions.DuplicationException;
import model.entries.Mineral;
import model.entries.WikiEntry;
import utils.fieldnames.Attributes;
import model.modelexceptions.EmptyTableException;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.MineralDuplicateException;
import org.json.JSONObject;

import java.util.*;

// Implementation of WikiEntryTable for storing a collection of minerals

public class MineralTable implements WikiEntryTable {

    private final HashMap<String, Mineral> mineralNameTable;

    // EFFECTS: constructs the MineralTable with mineralNameTable initialized to a new HashMap
    public MineralTable() {
        this.mineralNameTable = new HashMap<>();
    }

    // getters
    public HashMap<String, Mineral> getMineralNameTable() {
        return this.mineralNameTable;
    }

    // EFFECTS: returns a Mineral List ordered/grouped by the provided attribute
    public List<Mineral> getTableSortedBy(Attributes attribute) throws EmptyTableException {
        if (this.mineralNameTable.isEmpty()) {
            throw new EmptyTableException();
        }
        List<Mineral> returnList = new ArrayList<>(this.mineralNameTable.values());
        switch (attribute) {
            case IOR: returnList.sort((Mineral m1, Mineral m2) ->
                        Float.compare(m1.getIndexOfRefraction(), m2.getIndexOfRefraction()));
                break;
            case HARDNESS: returnList.sort((Mineral m1, Mineral m2) -> Float.compare(m1.getHardness(),
                    m2.getHardness()));
                break;
            case DENSITY: returnList.sort((Mineral m1, Mineral m2) -> Float.compare(m1.getDensity(), m2.getDensity()));
                break;
            case CRYSTAL: returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCrystalStructure().ordinal()));
                break;
            case CLEAVAGE: returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCleavage().ordinal()));
                break;
            default:
                break;
        }
        return returnList;
    }

    // EFFECTS: returns the entry with the corresponding name
    //          throws ItemNotFoundException if there is no entry with the provided key
    @Override
    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        Mineral requestedMineral = mineralNameTable.get(name);
        if (requestedMineral != null) {
            return requestedMineral;
        }

        throw new ItemNotFoundException();
    }

    // MODIFIES: this
    // EFFECTS: adds a KV pair of the entry name and the entry to the mineralNameTable
    //          throws DuplicateException if the key is already in mineralNameTable
    @Override
    public void addEntry(WikiEntry entry) throws DuplicationException {
        if (mineralNameTable.get(entry.getName()) == null) {
            mineralNameTable.put(entry.getName(), (Mineral) entry);
        } else {
            throw new MineralDuplicateException();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the KV pair corresponding to name from mineralNameTable
    //          throws ItemNotFoundException if name is not a key
    @Override
    public void removeEntry(String name) throws ItemNotFoundException {

        if (mineralNameTable.get(name) != null) {
            mineralNameTable.remove(name);
        } else {
            throw new ItemNotFoundException();
        }
    }

    // EFFECTS: produces a 2D String array with the rows being in order of given attribute
    //          of Minerals converted to array
    @Override
    public String[][] getTableAsArray(Attributes attribute) {
        List<Mineral> mineralValues;
        try {
            mineralValues = getTableSortedBy(attribute);
        } catch (EmptyTableException e) {
            mineralValues = new ArrayList<>(mineralNameTable.values());
        }
        String[][] mineralArray = new String[mineralValues.size()][];
        for (int i = 0; i < mineralValues.size(); i++) {
            mineralArray[i] = mineralValues.get(i).giveAttributeAsObjects();
        }
        return mineralArray;
    }

    // EFFECTS: returns a new JSONObject containing each value in mineralNameTable converted to a JSONObject
    public JSONObject toJson() {
        JSONObject tableJson = new JSONObject();
        for (Mineral mineral : mineralNameTable.values()) {
            tableJson.put(mineral.getName(), mineral.toJson());
        }
        return tableJson;
    }
}

