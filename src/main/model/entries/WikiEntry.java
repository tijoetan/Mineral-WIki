package model.entries;

// Abstract class for Mineral and Family Classes

import model.chemicalstructure.Formula;

public abstract class WikiEntry {
    protected String description;
    protected Formula generalFormula;
    protected String name;

    protected WikiEntry(String name) {
        this.name = name;
        this.description = "No description provided";
        this.generalFormula = new Formula();
    }

    public void setDescription(String description) {
        if (!description.isEmpty()) {
            this.description = description;
        }
    }

    public void setGeneralFormula(Formula formula) {
        if (formula.isValidFormula()) {
            this.generalFormula = formula;
        }
    }

    public String getName() {
        return this.name;
    }

    public Formula getGeneralFormula() {
        return this.generalFormula;
    }

    public abstract String giveAllAttributes();

    public String getDescription() {
        return (this.description);
    }

}
