package model.entries;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JsonFieldNames;

import java.util.ArrayList;
import java.util.List;

// Mineral family data abstraction

public class Family extends WikiEntry {
    private final List<WikiEntry> mineralsWithFamily;

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
    public void addMineralsWithFamily(List<WikiEntry> minerals) {
        for (WikiEntry mineral : minerals) {
            if (!mineralsWithFamily.contains(mineral)) {
                mineralsWithFamily.add(mineral);
            }

        }
    }

    // EFFECTS: prints out family name, formula and minerals with this family
    @Override
    public String giveAllAttributes() {
        StringBuilder relatedEntries;
        relatedEntries = new StringBuilder();
        for (WikiEntry sub : mineralsWithFamily) {
            relatedEntries.append(sub.getName());
            relatedEntries.append(" ");
        }
        String row = String.format("Name: %s | General Formula: %s | Subs: %s",
                name,
                generalFormula.getFormulaAsString(),
                relatedEntries);
        return row;
    }

    @Override
    public JSONObject toJson() {
        JSONObject familyJson = super.toJson();
        familyJson.put(JsonFieldNames.MINERALS_OF_FAMILY, mineralNamesToJsonArray());
        return familyJson;
    }

    private JSONArray mineralNamesToJsonArray() {
        JSONArray mineralNamesJson = new JSONArray();
        for (WikiEntry mineral : mineralsWithFamily) {
            mineralNamesJson.put(mineral.getName());
        }
        return mineralNamesJson;
    }
}
