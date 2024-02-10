package model;

import model.exceptions.MineralDuplicateException;
import model.tableentry.FamilyTableEntry;
import model.tableentry.MineralTableEntry;
import model.tableentry.TableEntry;

import java.util.*;

public class MineralTable {

    private final HashMap<String, MineralTableEntry> mineralNameTable;
    private final HashMap<String, FamilyTableEntry> familyNameTable;


    public MineralTable() {
        this.mineralNameTable = new HashMap<>();
        this.familyNameTable = new HashMap<>();

    }

    public HashMap<String, MineralTableEntry> getMineralNameTable() {
        return mineralNameTable;
    }

    public HashMap<String, FamilyTableEntry> getFamilyNameTable() {
        return familyNameTable;
    }

    public WikiEntry getRequestedEntry(String name) {
        TableEntry requestedMineral = mineralNameTable.get(name);
        TableEntry requestedFamily = familyNameTable.get(name);
        if (requestedMineral != null) {
            return requestedMineral.getEntry();
        }

        if (requestedFamily != null) {
            return requestedFamily.getEntry();
        }

        return null;
    }

    public void addMineral(Mineral mineral) throws MineralDuplicateException {
        if (mineralNameTable.get(mineral.getName()) == null) {
            mineralNameTable.put(mineral.getName(),
                    new MineralTableEntry(mineral, mineral.getGeneralFormula(), mineral.getCrystalStructure()));
            System.out.println("Finished Adding");
        } else {
            throw new MineralDuplicateException();
        }
    }

    public void addFamily(Family family) throws MineralDuplicateException {
        if (familyNameTable.get(family.getName()) == null) {
            familyNameTable.put(family.getName(), new FamilyTableEntry(family, family.getGeneralFormula()));
        } else {
            throw new MineralDuplicateException();
        }
    }

}
