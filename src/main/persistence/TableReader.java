package persistence;

import model.entries.Mineral;
import model.entries.WikiEntry;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

// Reads WikiEntryTable from JSON
public class TableReader {
    private String source;

    public TableReader(String source) {
        this.source = source;
    }

    public JSONObject readFile() throws IOException {
        StringBuilder sourceStream = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(sourceStream::append);
        }
        return new JSONObject(sourceStream.toString());
    }

    public void setUpMineralTable(HashMap<String, Mineral> mineralTable) {


    }

    public void setUpFamilyTable(HashMap<String, WikiEntry> familyTable) {

    }
}
