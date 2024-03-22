package model.entries;

// Abstract class for Mineral and Family Classes

import model.chemicalstructure.Formula;
import org.json.JSONObject;
import persistence.Writable;
import utils.WrapString;
import utils.fieldnames.Constants;
import utils.fieldnames.JsonFieldNames;

public abstract class WikiEntry implements Writable {
    protected String description;
    protected Formula generalFormula;
    protected String name;

    // EFFECTS: constructs WikiEntry with given name
    protected WikiEntry(String name) {
        this.name = name;
        this.description = "No description provided";
        this.generalFormula = new Formula();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Formula getGeneralFormula() {
        return this.generalFormula;
    }

    public String getDescription() {
        return (this.description);
    }

    // MODIFIES: this
    // EFFECTS: sets description if provided description is not an empty string
    public void setDescription(String description) {
        if (!description.isEmpty()) {
            this.description = WrapString.wrapString(description,
                    Constants.MAX_LINE_LENGTH,
                    Constants.WRAP_FOR_GUI);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets formula if formula.isValidFormula()
    public void setGeneralFormula(Formula formula) {
        if (formula.isValidFormula()) {
            this.generalFormula = formula;
        }
    }

    // EFFECTS: produces JSON object with the fields of the instance
    @Override
    public JSONObject toJson() {
        JSONObject wikiJson = new JSONObject();
        wikiJson.put(JsonFieldNames.NAME, name);
        wikiJson.put(JsonFieldNames.FORMULA, generalFormula.getFormulaAsString());
        wikiJson.put(JsonFieldNames.DESCRIPTION, description);
        return wikiJson;
    }

    public abstract String giveAllAttributes();

    public abstract String[] giveAttributeAsObjects();

}
