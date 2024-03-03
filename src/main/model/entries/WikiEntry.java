package model.entries;

// Abstract class for Mineral and Family Classes

import model.chemicalstructure.Formula;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public abstract class WikiEntry implements Writable {
    protected String description;
    protected Formula generalFormula;
    protected String name;

    protected WikiEntry(String name) {
        this.name = name;
        this.description = "No description provided";
        this.generalFormula = new Formula();
    }

    public void setDescription(String description) {
        if (!(description == null || description.isEmpty())) {
            this.description = description;
        }
    }

    public void setGeneralFormula(Formula formula) {
        if (formula != null && formula.isValidFormula()) {
            this.generalFormula = formula;
        }
    }

    public String getName() {
        return this.name;
    }

    public Formula getGeneralFormula() {
        return this.generalFormula;
    }

    public String getDescription() {
        return (this.description);
    }

    public abstract String giveAllAttributes();

    @Override
    public JSONObject toJson() {
        JSONObject wikiJson = new JSONObject();
        wikiJson.put("name", name);
        wikiJson.put("generalFormula", generalFormula.getFormulaAsString());
        wikiJson.put("description", description);
        return wikiJson;
    }

}
