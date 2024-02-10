package model;

// Abstract class for Mineral and Family Classes

import chemicalstructure.Formula;
import java.util.ArrayList;
import java.util.List;

public abstract class WikiEntry {
    protected String description;
    protected List<WikiEntry> related;
    protected Formula generalFormula;
    protected String name;

    protected WikiEntry(String name) {
        this.related = new ArrayList<>();
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeneralFormula(Formula formula) {
        this.generalFormula = formula;
    }

    public void addSubs(WikiEntry wikiEntry) {
        this.related.add(wikiEntry);
    }

    public String getName() {
        return this.name;
    }

    public Formula getGeneralFormula() {
        return this.generalFormula;
    }
}
