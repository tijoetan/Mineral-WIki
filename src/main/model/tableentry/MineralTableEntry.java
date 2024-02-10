package model.tableentry;

import chemicalstructure.Formula;
import model.CrystalStructure;
import model.WikiEntry;

public class MineralTableEntry extends TableEntry {
    CrystalStructure crystalStructure;

    public MineralTableEntry(WikiEntry entry, Formula chemicalFormula, CrystalStructure crystalStructure) {
        super(entry, chemicalFormula);
        this.crystalStructure = crystalStructure;
    }
}
