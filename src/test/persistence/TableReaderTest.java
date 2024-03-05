package persistence;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.CrystalStructure;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableReaderTest {

    TableReader testReader;
    FamilyTable familyTable;
    MineralTable mineralTable;

    @BeforeEach
    void runBefore() {
        familyTable = new FamilyTable();
        mineralTable = new MineralTable();
    }

    @Test
    void testReadFileNoException() {
        testReader = new TableReader("data/tests/testValidFileRead.json", familyTable, mineralTable);
        JSONObject readJson = null;
        try {
            readJson =  testReader.readFile();
        } catch (IOException | InvalidFileException e) {
            fail();
        }

        assertTrue(readJson.getBoolean("read"));
    }

    @Test
    void testReadFileExpectIoException() {
        testReader = new TableReader("data/tests/thisFileDoesNotExist.json", familyTable, mineralTable);
        try {
            testReader.readFile();
            fail();
        } catch (IOException e) {
            // Expected
        } catch (InvalidFileException e) {
            fail();
        }
    }

    @Test
    void testReadFileExpectInvalidFileException() {
        testReader = new TableReader("data/tests/testInvalidFileRead.json", familyTable, mineralTable);
        try {
            testReader.readFile();
            fail();
        } catch (IOException e) {
            fail();
        } catch (InvalidFileException e) {
            // Expected
        }
    }

    @Test
    void testSetupTablesNoExceptions() {;
        try {
            testReader = new TableReader("data/tests/testStandardDatabase.json",
                    familyTable,
                    mineralTable);
            testReader.setupTables();
        } catch (IOException | InvalidFileException e) {
            fail();
        }
        try {
            // Checking that it is contained
            mineralTable.getRequestedEntry("Diamond");
            mineralTable.getRequestedEntry("Orthoclase");
            mineralTable.getRequestedEntry("Labradorite");

            // Checking Linking
            List<WikiEntry> familyMinerals = ((Family) familyTable.getRequestedEntry("Feldspar")).
                    getMineralsWithFamily();
            assertEquals(2, familyMinerals.size());
            assertEquals("Orthoclase", familyMinerals.get(0).getName());
            assertEquals("Labradorite", familyMinerals.get(1).getName());

        } catch (ItemNotFoundException e) {
            fail();
        }
    }

    @Test
    void setUpMineralTableExpectInvalidFileException() {
        testReader = new TableReader("data/tests/testInvalidFileRead.json",
                familyTable,
                mineralTable);
        try {
            testReader.setupTables();
            fail();
        } catch (IOException e) {
           fail();
        } catch (InvalidFileException e) {
            // Expected
        }
    }

    @Test
    void setUpMineralTableExpectIOException() {
        testReader = new TableReader("",
                familyTable,
                mineralTable);
        try {
            testReader.setupTables();
            fail();
        } catch (IOException e) {
            // Expected
        } catch (InvalidFileException e) {
            fail();
        }
    }

    @Test
    void setUpFamily() {
    }
}