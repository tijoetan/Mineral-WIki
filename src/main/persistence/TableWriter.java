package persistence;

// Writes WikiEntryTable to JSON

import model.tableentry.WikiEntryTable;
import org.json.JSONObject;
import utils.JsonFieldNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TableWriter {
    private String destination;
    private PrintWriter writer;

    public TableWriter(String destination) throws InvalidFileException {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        writer =  new PrintWriter(new File(destination));
    }

    public void close() {
        writer.close();
    }

    public void writeToDestination(WikiEntryTable mineralTable, WikiEntryTable familyTable) {
        JSONObject mineralTableJson = mineralTable.toJson();
        JSONObject familyTableJson = familyTable.toJson();
        JSONObject bothTableJson = new JSONObject();
        bothTableJson.put(JsonFieldNames.MINERALS, mineralTableJson);
        bothTableJson.put(JsonFieldNames.FAMILIES, familyTableJson);

        writer.print(bothTableJson.toString(4));
    }
}
