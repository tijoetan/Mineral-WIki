package persistence;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.CrystalStructure;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.FillWikiEntry;
import utils.JsonFieldNames;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Reads WikiEntryTable from JSON
public class TableReader {
    private final String source;
    private final FamilyTable familyTable;
    private final MineralTable mineralTable;

    public TableReader(String source, FamilyTable familyTable, MineralTable mineralTable) {
        this.source = source;
        this.familyTable = familyTable;
        this.mineralTable = mineralTable;
    }

    public JSONObject readFile() throws IOException, InvalidFileException {
        StringBuilder sourceStream = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(sourceStream::append);
        }
        try {
            return new JSONObject(sourceStream.toString());
        } catch (JSONException e) {
            throw new InvalidFileException();
        }
    }

    public void setupTables() throws IOException, InvalidFileException {
        JSONObject readFile = readFile();
        JSONObject mineralJson = readFile.getJSONObject(JsonFieldNames.MINERALS);
        JSONObject familyJson = readFile.getJSONObject(JsonFieldNames.FAMILIES);
        setUpMineralTable(mineralJson);
        setUpFamilyTable(familyJson);
    }

    public void setUpMineralTable(JSONObject mineralJson) {
        for (String s : JSONObject.getNames(mineralJson)) {
            Mineral newEntry = setupMineral(mineralJson.getJSONObject(s));
            mineralTable.getMineralNameTable().put(newEntry.getName(), newEntry);
        }
    }

    public Mineral setupMineral(JSONObject mineralData) {
        Mineral mineral = new Mineral(mineralData.getString(JsonFieldNames.NAME));
        FillWikiEntry.fillMineral(mineral,
                getFormula(mineralData.getString(JsonFieldNames.FORMULA)),
                CrystalStructure.valueOf(mineralData.getString(JsonFieldNames.CRYSTAL_STRUCTURE)),
                mineralData.getFloat(JsonFieldNames.HARDNESS),
                mineralData.getFloat(JsonFieldNames.DENSITY),
                mineralData.getFloat(JsonFieldNames.INDEX_OF_REFRACTION),
                mineralData.getString(JsonFieldNames.DESCRIPTION));
        return mineral;
    }

    public Family setUpFamily(JSONObject familyJson) {
        Family family = new Family(familyJson.getString(JsonFieldNames.NAME));
        FillWikiEntry.fillFamily(family,
                getFormula(familyJson.getString(JsonFieldNames.FORMULA)),
                getRelatedMinerals(familyJson.getJSONArray(JsonFieldNames.MINERALS_OF_FAMILY)),
                familyJson.getString(JsonFieldNames.DESCRIPTION));
        return family;

    }

    public void setUpFamilyTable(JSONObject familyJson) {
        for (String s : JSONObject.getNames(familyJson)) {
            Family newFamily = setUpFamily(familyJson.getJSONObject(s));
            familyTable.getFamilyNameTable().put(newFamily.getName(), newFamily);
        }
    }

    public List<WikiEntry> getRelatedMinerals(JSONArray mineralsWithFamilyName) {
        List<WikiEntry> relatedMinerals = new ArrayList<>();
        for (int i = 0; i < mineralsWithFamilyName.length(); i++) {
            try {
                WikiEntry mineral = mineralTable.getRequestedEntry(mineralsWithFamilyName.getString(i));
                relatedMinerals.add(mineral);
            } catch (ItemNotFoundException e) {
                //
            }
        }
        return relatedMinerals;
    }

    public static Formula getFormula(String mineralFormulaName) {
        try {
            return mineralFormulaName.isEmpty() ? new Formula() : new Formula(mineralFormulaName);
        } catch (UnknownElementException e) {
            return new Formula();
        }
    }
}
