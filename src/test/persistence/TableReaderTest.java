package persistence;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.ItemNotFoundException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.fieldnames.JsonFieldNames;

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
            readJson = testReader.readFile();
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
    void setUpMineralLinkingImproper() {
        try {
            mineralTable.addEntry(new Mineral("Diamond"));
            mineralTable.addEntry(new Mineral("Ruby"));
        } catch (DuplicationException e) {
            fail();
        }
        testReader = new TableReader("data/tests/testFamilyTableSetupWithDuplication.json",
                familyTable,
                mineralTable);
        try {
            JSONArray familyLinks = testReader.readFile().
                    getJSONObject("Feldspar").
                    getJSONArray(JsonFieldNames.MINERALS_OF_FAMILY);
            assertTrue(testReader.getRelatedMinerals(familyLinks).isEmpty());
        } catch (IOException | InvalidFileException e) {
            fail();
        }
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

/*
z = 6(x^2)^(5/2)
Perfectly circular

z = 6(x^2)^(5/2)
z = 6(r^2)^(5/2)
z = 6(r^5)
z/6 = (r^5)
(z/6)^(2/5) = r^2
pi(z/6)^(2/5) = pi*r^2

Integrate[pi(z/6)^(2/5), {z, 0, z}] = V
pi*5/7(z/6)^(7/5)*6 = V
V = 30pi/7(z/6)^(7/5)
*/