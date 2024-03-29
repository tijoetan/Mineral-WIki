package model.entries;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.fieldnames.JsonFieldNames;

import java.util.ArrayList;
import java.util.List;

// Mineral family data abstraction

public class Family extends WikiEntry {
    private List<WikiEntry> mineralsWithFamily;

    // EFFECTS: calls super with name and initializes mineralsWithFamily
    public Family(String name) {
        super(name);
        this.mineralsWithFamily = new ArrayList<>();
    }

    // getters
    public List<WikiEntry> getMineralsWithFamily() {
        return this.mineralsWithFamily;
    }

    // MODIFIES: this
    // EFFECTS: adds mineral to mineralsWithFamily if it is not already there
    public void setMineralsWithFamily(List<WikiEntry> minerals) {
        mineralsWithFamily = minerals;
    }

    // EFFECTS: prints out family name, formula and minerals with this family
    @Override
    public String giveAllAttributes() {
        return String.format("Name: %s | General Formula: %s | Subs: %s",
                name,
                generalFormula.getUnparsedFormula(),
                getRelatedEntries());
    }

    // EFFECTS: Produces a string containing all the descendant names separated by line breaks
    private StringBuilder getRelatedEntries() {
        StringBuilder relatedEntries = new StringBuilder();
        for (WikiEntry sub : mineralsWithFamily) {
            relatedEntries.append(sub.getName());
            relatedEntries.append('\n');
        }
        return relatedEntries;
    }

    // EFFECTS: Produces an array of all the Family attributes as Strings
    public String[] giveAttributeAsObjects() {
        return new String[]{name,
                generalFormula.getFormulaAsString(),
                getRelatedEntries().toString()};
    }

    // EFFECTS: converts mineral attributes to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject familyJson = super.toJson();
        familyJson.put(JsonFieldNames.MINERALS_OF_FAMILY, mineralNamesToJsonArray());
        return familyJson;
    }

    // EFFECTS: returns a JSONArray containing the names of mineralsWithFamily
    public JSONArray mineralNamesToJsonArray() {
        JSONArray mineralNamesJson = new JSONArray();
        for (WikiEntry mineral : mineralsWithFamily) {
            mineralNamesJson.put(mineral.getName());
        }
        return mineralNamesJson;
    }
}
