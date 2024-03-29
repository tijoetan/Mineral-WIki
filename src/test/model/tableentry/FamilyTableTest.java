package model.tableentry;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.WikiEntry;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.fieldnames.Attributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTableTest {

    FamilyTable testTable;

    Family f1;
    Family f2;
    Family f3;


    @BeforeEach
    void beforeEach() {
        testTable = new FamilyTable();

        f1 = new Family("First");
        f2 = new Family("Second");
        f3 = new Family("Third");

        try {
            f1.setGeneralFormula(new Formula("HgS"));
            f2.setGeneralFormula(new Formula("PbS"));
            f3.setGeneralFormula(new Formula("CdS"));
        } catch (UnknownElementException e) {
            fail();
        }
    }
    @Test
    void testConstruction() {
       assertTrue(testTable.getFamilyNameTable().isEmpty());
    }

    @Test
    void testAddEntryNoDuplicates() {
        try {
            testTable.addEntry(f1);
            testTable.addEntry(f2);
            testTable.addEntry(f3);
        } catch (DuplicationException e) {
           fail();
        }

        assertTrue(testTable.getFamilyNameTable().containsKey("First"));
        assertTrue(testTable.getFamilyNameTable().containsKey("Second"));
        assertTrue(testTable.getFamilyNameTable().containsKey("Third"));

        assertEquals(f1, testTable.getFamilyNameTable().get("First"));
        assertEquals(f2, testTable.getFamilyNameTable().get("Second"));
        assertEquals(f3, testTable.getFamilyNameTable().get("Third"));
    }

    @Test
    void testAddEntryExpectDuplicationException() {
        try {
            testTable.addEntry(f1);
            testTable.addEntry(f1);
            fail();
        } catch (DuplicationException e) {
            // Expected
        }
    }

    @Test
    void testGetRequestedEntryItemIsPresent() {
        try {
            testTable.addEntry(f1);
            testTable.addEntry(f2);
            testTable.addEntry(f3);
        } catch (DuplicationException e) {
            fail();
        }

        try {
            assertEquals(f1, testTable.getRequestedEntry("First"));
            assertEquals(f2, testTable.getRequestedEntry("Second"));
            assertEquals(f3, testTable.getRequestedEntry("Third"));
        } catch (ItemNotFoundException e) {
            fail();
        }
    }

    @Test
    void testGetRequestedEntryItemIsNotPresent() {
        try {
            testTable.getRequestedEntry("First");
            fail();
        } catch (ItemNotFoundException e) {
            //Expected
        }
    }

    @Test
    void testRemoveEntryItemIsPresent() {
        try {
            testTable.addEntry(f1);
            testTable.addEntry(f2);
            testTable.addEntry(f3);
        } catch (DuplicationException e) {
            fail();
        }


        try {
            testTable.removeEntry("First");
            testTable.removeEntry("Second");
        } catch (ItemNotFoundException e) {
            fail();
        }

        assertFalse(testTable.getFamilyNameTable().containsKey("First"));
        assertFalse(testTable.getFamilyNameTable().containsValue(f1));

        assertFalse(testTable.getFamilyNameTable().containsKey("Second"));
        assertFalse(testTable.getFamilyNameTable().containsValue(f2));

        assertTrue(testTable.getFamilyNameTable().containsKey("Third"));
        assertTrue(testTable.getFamilyNameTable().containsValue(f3));
    }

    @Test
    void testRemoveEntryItemIsNotPresent() {
        try {
            testTable.removeEntry("First");
            fail();
        } catch (ItemNotFoundException e) {
            // Expected
        }
    }

    @Test
    void testGetFamilies() {
        try {
            testTable.addEntry(f1);
            testTable.addEntry(f2);
            testTable.addEntry(f3);
        } catch (DuplicationException e) {
            fail();
        }

        List<WikiEntry> familyList = testTable.getFamilies();

        assertEquals(3, familyList.size());

        assertTrue(familyList.contains(f1));
        assertTrue(familyList.contains(f2));
        assertTrue(familyList.contains(f3));
    }

    @Test
    void testGetTableAsArray() {
        testGetFamilies();
        String[][] testArray = testTable.getTableAsArray(Attributes.DEFAULT);
        assertEquals(3, testArray.length);
        for (int i = 0; i < 3; i++) {
            assertEquals(testArray[0][i], f2.giveAttributeAsObjects()[i]);
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(testArray[1][i], f3.giveAttributeAsObjects()[i]);
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(testArray[2][i], f1.giveAttributeAsObjects()[i]);
        }
    }
}