package model.tableentry;

import model.chemicalstructure.Formula;
import model.enums.CrystalStructure;
import model.entries.WikiEntry;

public class MineralTableEntry extends TableEntry {
    CrystalStructure crystalStructure;
    float indexOfRefraction;
    float density;
    float hardness;

    public MineralTableEntry(WikiEntry entry, Formula chemicalFormula, CrystalStructure crystalStructure,
                             float indexOfRefraction, float hardness, float density) {
        super(entry);
        this.crystalStructure = crystalStructure;
        this.indexOfRefraction = indexOfRefraction;
        this.hardness = hardness;
        this.density = density;
    }


}
