package persistence;

import model.entries.Family;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
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
    void testSetupTablesNoExceptions() {
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
            List<WikiEntry> familyMinerals1 = ((Family) familyTable.getRequestedEntry("Feldspar")).
                    getMineralsWithFamily();
            assertEquals(2, familyMinerals1.size());
            assertEquals("Orthoclase", familyMinerals1.get(0).getName());
            assertEquals("Labradorite", familyMinerals1.get(1).getName());

            List<WikiEntry> familyMinerals2 = ((Family) familyTable.getRequestedEntry("Gemstones")).
                    getMineralsWithFamily();
            assertEquals(2, familyMinerals2.size());
            assertEquals("Diamond", familyMinerals2.get(0).getName());
            assertEquals("Beryl", familyMinerals2.get(1).getName());

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

    @Test
    void testGetFormula() {
        // Valid Formula
        assertTrue(TableReader.getFormula("NaOH").isValidFormula());
        // Invalid Formula
        assertFalse(TableReader.getFormula("A").isValidFormula());
        //Blank String
        assertFalse(TableReader.getFormula("").isValidFormula());
    }
}