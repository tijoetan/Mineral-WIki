package model.tableentry;

import model.modelexceptions.DuplicationException;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.modelexceptions.EmptyTableException;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.MineralDuplicateException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MineralTable implements WikiEntryTable {

    private final HashMap<String, Mineral> mineralNameTable;

    public MineralTable() {
        this.mineralNameTable = new HashMap<>();
    }

    public HashMap<String, Mineral> getMineralNameTable() {
        return this.mineralNameTable;
    }

    public List<Mineral> getTableSortedBy(Attributes attribute) throws EmptyTableException {
        if (this.mineralNameTable.isEmpty()) {
            throw new EmptyTableException();
        }
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
            case CRYSTAL:
                returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCrystalStructure().ordinal()));
            case CLEAVAGE:
                returnList.sort(Comparator.comparingInt((Mineral m) -> m.getCleavage().ordinal()));
                break;
        }
        return returnList;
    }

    @Override
    public WikiEntry getRequestedEntry(String name) throws ItemNotFoundException {
        Mineral requestedMineral = mineralNameTable.get(name);
        if (requestedMineral != null) {
            return requestedMineral;
        }

        throw new ItemNotFoundException();
    }

    @Override
    public void addEntry(WikiEntry entry) throws DuplicationException {
        if (mineralNameTable.get(entry.getName()) == null) {
            mineralNameTable.put(entry.getName(), (Mineral) entry);
        } else {
            throw new MineralDuplicateException();
        }
    }

    @Override
    public void removeEntry(String name) throws ItemNotFoundException {

        if (mineralNameTable.get(name) != null) {
            mineralNameTable.remove(name);
        } else {
            throw new ItemNotFoundException();
        }
    }
}

