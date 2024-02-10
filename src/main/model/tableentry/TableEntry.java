package model.tableentry;

import chemicalstructure.Formula;
import model.WikiEntry;

public class TableEntry {
    protected WikiEntry entry;
    protected Formula chemicalFormula;

    public TableEntry(WikiEntry entry, Formula chemicalFormula) {
        this.entry = entry;
        this.chemicalFormula = chemicalFormula;
    }

    public Formula getChemicalFormula() {
        return chemicalFormula;
    }

    public void setChemicalFormula(Formula chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public WikiEntry getEntry() {
        return entry;
    }

    public void setEntry(WikiEntry entry) {
        this.entry = entry;
    }
}
