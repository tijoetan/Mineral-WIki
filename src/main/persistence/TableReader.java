package persistence;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.logging.Event;
import model.logging.EventLog;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.fieldnames.JsonFieldNames;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Parses a .json file into a populated FamilyTable and MineralTable

public class TableReader {
    private final String source;
    private final FamilyTable familyTable;
    private final MineralTable mineralTable;

    // EFFECTS: constructs TableReader with provided source path, and relevant family and mineralTables to write to
    public TableReader(String source, FamilyTable familyTable, MineralTable mineralTable) {
        this.source = source;
        this.familyTable = familyTable;
        this.mineralTable = mineralTable;
    }

    // EFFECTS: reads the path from the source and returns it converted to a JSONObject
    //          throws IOException if the source cannot be read
    //          throws InvalidFileException if the source file does not correspond to valid JSON
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

    // REQUIRES: if a valid JSON file is given by source then it must be a valid format and has at least
    //              1 Mineral and 1 Family
    // MODIFIES: this
    // EFFECTS: populates mineralTable and familyTable with the data stored in source
    public void setupTables() throws IOException, InvalidFileException {
        JSONObject readFile = readFile();
        JSONObject mineralJson = readFile.getJSONObject(JsonFieldNames.MINERALS);
        JSONObject familyJson = readFile.getJSONObject(JsonFieldNames.FAMILIES);
        setUpMineralTable(mineralJson);
        setUpFamilyTable(familyJson);
        EventLog.getInstance().logEvent(new Event("Database loaded from: " + source));

    }

    // REQUIRES: the mineralJson given must be recognizable
    // MODIFIES: this
    // EFFECTS: adds the entries in mineralJson to mineralTable
    public void setUpMineralTable(JSONObject mineralJson) {
        for (String s : JSONObject.getNames(mineralJson)) {
            Mineral newEntry = setupMineral(mineralJson.getJSONObject(s));
            mineralTable.getMineralNameTable().put(newEntry.getName(), newEntry);
        }
    }

    // REQUIRES: this given mineralData must be recognizable
    // EFFECTS: returns a new mineral with the fields based on the mineralData
    public Mineral setupMineral(JSONObject mineralData) {
        Mineral mineral = new Mineral(mineralData.getString(JsonFieldNames.NAME));
        Mineral.fillMineral(mineral,
                getFormula(mineralData.getString(JsonFieldNames.FORMULA)),
                CrystalStructure.valueOf(mineralData.getString(JsonFieldNames.CRYSTAL_STRUCTURE)),
                mineralData.getFloat(JsonFieldNames.HARDNESS),
                mineralData.getFloat(JsonFieldNames.DENSITY),
                mineralData.getFloat(JsonFieldNames.INDEX_OF_REFRACTION),
                mineralData.getString(JsonFieldNames.DESCRIPTION),
                Cleavage.valueOf(mineralData.getString(JsonFieldNames.CLEAVAGE)));
        return mineral;
    }

    // REQUIRES: given familyJson can be parsed
    // MODIFIES: this
    // EFFECTS: fills familyTable with the families indicated in familyJson
    public void setUpFamilyTable(JSONObject familyJson) {
        for (String s : JSONObject.getNames(familyJson)) {
            Family newFamily = setUpFamily(familyJson.getJSONObject(s));
            familyTable.getFamilyNameTable().put(newFamily.getName(), newFamily);
        }
    }

    // REQUIRES: given familyData can be parsed
    // EFFECTS: produces a new family with the fields setup by familyData
    public Family setUpFamily(JSONObject familyData) {
        Family family = new Family(familyData.getString(JsonFieldNames.NAME));
        Family.fillFamily(family,
                getFormula(familyData.getString(JsonFieldNames.FORMULA)),
                getRelatedMinerals(familyData.getJSONArray(JsonFieldNames.MINERALS_OF_FAMILY)),
                familyData.getString(JsonFieldNames.DESCRIPTION));
        return family;

    }

    // EFFECTS: returns a new List containing minerals from mineralTable that have the same name as
    //          mineralsWithFamilyName
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

    // EFFECTS: returns a new Formula based on the mineralFormulaName
    //          if mineralFormulaName is empty or causes an error, a dummy formula is returned
    public static Formula getFormula(String mineralFormulaName) {
        try {
            return mineralFormulaName.isEmpty() ? new Formula() : new Formula(mineralFormulaName);
        } catch (UnknownElementException e) {
            return new Formula();
        }
    }
}
