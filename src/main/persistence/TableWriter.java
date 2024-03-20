package persistence;

// Writes WikiEntryTable to JSON

import model.tableentry.WikiEntryTable;
import org.json.JSONObject;
import utils.fieldnames.JsonFieldNames;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TableWriter {
    private final String destination;
    private PrintWriter writer;

    // EFFECTS: sets this.destination to destination
    public TableWriter(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: assigns writer to a new PrintWriter with the provided destination
    //          throws FileNotFoundException if destination cannot be resolved to a path
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // EFFECTS: converts given mineralTable and familyTable to a JSONObject and writes it to the destination
    public void writeToDestination(WikiEntryTable mineralTable, WikiEntryTable familyTable) {
        JSONObject mineralTableJson = mineralTable.toJson();
        JSONObject familyTableJson = familyTable.toJson();
        JSONObject bothTableJson = new JSONObject();
        bothTableJson.put(JsonFieldNames.MINERALS, mineralTableJson);
        bothTableJson.put(JsonFieldNames.FAMILIES, familyTableJson);

        writer.print(bothTableJson.toString(4));
    }
}
