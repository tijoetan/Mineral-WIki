package model.tableentry;

import model.chemicalstructure.Formula;
import model.entries.WikiEntry;

public class TableEntry {
    protected WikiEntry entry;
    protected Formula chemicalFormula;

    public TableEntry(WikiEntry entry) {
        this.entry = entry;
        this.chemicalFormula = entry.getGeneralFormula();

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
