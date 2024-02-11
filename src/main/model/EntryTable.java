package model;

import model.enums.Attributes;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.EntryType;
import model.exceptions.ItemNotFoundException;
import model.exceptions.MineralDuplicateException;

import java.util.*;

public class EntryTable {
    private final HashMap<String, Mineral> mineralNameTable;
    private final HashMap<String, Family> familyNameTable;


    public EntryTable() {
        this.mineralNameTable = new HashMap<>();
        this.familyNameTable = new HashMap<>();

    }


    public List<Mineral> getMineralTableSortedBy(Attributes attribute) {
        List<Mineral> returnList = new ArrayList<>(this.mineralNameTable.values());
        switch (attribute) {
            case IOR:
                returnList.sort((Mineral m1, Mineral m2) ->
                        Float.compare(m1.getIndexOfRefraction(), m2.getIndexOfRefraction()));
                break;
            case HARDNESS:
                returnList.sort((Mineral m1, Mineral m2) -> Float.compare(m1.getHardness(), m2.getHardness()));
                break;
            case DENSITY:
                returnList.sort((Mineral m1, Mineral m2) -> Float.compare(m1.getDensity(), m2.getDensity()));
                break;
            case CRYSTAL_STRUCTURE:
                returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCrystalStructure().getValue()));
            case CLEAVAGE:
                returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCleavage().getValue()));
                break;
        }

        return returnList;

    }

    public HashMap<String, Mineral> getMineralNameTable() {
        return mineralNameTable;
    }

    public HashMap<String, Family> getFamilyNameTable() {
        return familyNameTable;
    }


    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        Mineral requestedMineral = mineralNameTable.get(name);
        Family requestedFamily = familyNameTable.get(name);
        if (requestedMineral != null) {
            return requestedMineral;
        }

        if (requestedFamily != null) {
            return requestedFamily;
        }

        throw new ItemNotFoundException();
    }


    public void addEntry(WikiEntry entry, EntryType type) throws MineralDuplicateException {
        if (type.equals(EntryType.MINERAL) && mineralNameTable.get(entry.getName()) == null) {
            mineralNameTable.put(entry.getName(), (Mineral) entry);
            System.out.println("Finished Adding");
        } else if (familyNameTable.get(entry.getName()) == null) {
            familyNameTable.put(entry.getName(), (Family) entry);

        } else {
            throw new MineralDuplicateException();
        }
    }


    public void removeEntry(String name, EntryType type) throws ItemNotFoundException {
        if (type.equals(EntryType.FAMILY) && familyNameTable.get(name) != null) {
            familyNameTable.remove(name);
        } else if (mineralNameTable.get(name) != null) {
            mineralNameTable.remove(name);
        } else {
            throw new ItemNotFoundException();
        }
    }
}

