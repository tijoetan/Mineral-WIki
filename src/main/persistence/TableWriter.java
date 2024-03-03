package persistence;

// Writes WikiEntryTable to JSON

import model.tableentry.WikiEntryTable;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public void writeToDestination(WikiEntryTable table) {
        JSONObject tableJson = table.toJson();
        writer.print(tableJson.toString(4));
    }
}
