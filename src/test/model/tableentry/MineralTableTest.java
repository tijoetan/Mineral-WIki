package model.tableentry;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.EmptyTableException;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MineralTableTest {

    MineralTable testTable;

    Mineral m1;
    Mineral m2;
    Mineral m3;
    Mineral m4;
    Mineral m5;

    @BeforeEach
    void beforeEach() throws UnknownElementException {
        testTable = new MineralTable();

        m1 = new Mineral("Quartz");
        m2 = new Mineral("Diamond");
        m3 = new Mineral("Corundum");
        m4 = new Mineral("Orthoclase");
        m5 = new Mineral("Acanthite");

        m1.setCleavage(Cleavage.NONE);
        m1.setHardness(7.0f);
        m1.setGeneralFormula(new Formula("SiO2"));
        m1.setIndexOfRefraction(1.54f);
        m1.setCrystalStructure(CrystalStructure.HEXAGONAL);
        m1.setDensity(2.65f);

        m2.setCleavage(Cleavage.OCTAHEDRAL);
        m2.setHardness(10.0f);
        m2.setGeneralFormula(new Formula("C"));
        m2.setIndexOfRefraction(2.41f);
        m2.setCrystalStructure(CrystalStructure.CUBIC);
        m2.setDensity(3.5f);


        m3.setCleavage(Cleavage.NONE);
        m3.setHardness(9.0f);
        m3.setGeneralFormula(new Formula("AlO3"));
        m3.setIndexOfRefraction(1.77f);
        m3.setCrystalStructure(CrystalStructure.TRIGONAL);
        m3.setDensity(4.0f);

        m4.setCleavage(Cleavage.BASAL);
        m4.setHardness(6.0f);
        m4.setGeneralFormula(new Formula("KAlSi3O8"));
        m4.setIndexOfRefraction(1.51f);
        m4.setCrystalStructure(CrystalStructure.MONOCLINIC);
        m4.setDensity(2.65f);


        m5.setCleavage(Cleavage.BASAL);
        m5.setHardness(2.5f);
        m5.setGeneralFormula(new Formula("Ag2S"));
        m5.setCrystalStructure(CrystalStructure.MONOCLINIC);
        m5.setDensity(7.3f);
    }

    @Test
    void testConstruction() {
        assertTrue(testTable.getMineralNameTable().isEmpty());
    }

    @Test
    void testAddEntryNoDuplicates() {
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m2);
            testTable.addEntry(m3);
        } catch (DuplicationException e) {
            fail();
        }

        assertTrue(testTable.getMineralNameTable().containsKey("Quartz"));
        assertTrue(testTable.getMineralNameTable().containsKey("Diamond"));
        assertTrue(testTable.getMineralNameTable().containsKey("Corundum"));

        assertTrue(testTable.getMineralNameTable().containsValue(m1));
        assertTrue(testTable.getMineralNameTable().containsValue(m2));
        assertTrue(testTable.getMineralNameTable().containsValue(m3));
    }

    @Test
    void testAddEntryExpectDuplicationException() {
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m1);
            fail();
        } catch (DuplicationException e) {
            // Expected
        }
    }

    @Test
    void testGetRequestedEntryItemIsPresent() {
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m2);
        } catch (DuplicationException e) {
            fail();
        }

        try {
            assertEquals(m1, testTable.getRequestedEntry("Quartz"));
            assertEquals(m2, testTable.getRequestedEntry("Diamond"));
        } catch (ItemNotFoundException e) {
            fail();
        }
    }

    @Test
    void testGetRequestedEntryItemIsNotPresent() {
        try {
            testTable.getRequestedEntry("Mineral");
            fail();
        } catch (ItemNotFoundException e) {
            // Expected
        }
    }

    @Test
    void testRemoveEntryItemIsThere() {
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m2);
            testTable.addEntry(m3);
            testTable.addEntry(m4);
        } catch (DuplicationException e) {
            fail();
        }

        try {
            testTable.removeEntry("Quartz");
            testTable.removeEntry("Diamond");
            testTable.removeEntry("Corundum");
        } catch (ItemNotFoundException e) {
            fail();
        }

        assertFalse(testTable.getMineralNameTable().containsKey("Quartz"));
        assertFalse(testTable.getMineralNameTable().containsKey("Diamond"));
        assertFalse(testTable.getMineralNameTable().containsKey("Corundum"));

        assertFalse(testTable.getMineralNameTable().containsValue(m1));
        assertFalse(testTable.getMineralNameTable().containsValue(m2));
        assertFalse(testTable.getMineralNameTable().containsValue(m3));

        assertTrue(testTable.getMineralNameTable().containsKey("Orthoclase"));
        assertTrue(testTable.getMineralNameTable().containsValue(m4));
    }

    @Test
    void testRemoveEntryItemNotPresent() {
        try {
            testTable.removeEntry("Beryl");
            fail();
        } catch (ItemNotFoundException e) {
            // Expected
        }
    }

    @Test
    void testGetTableSortedByNonEmptyTable() {
        List<Mineral> actualDensityGroup = null;
        List<Mineral> actualHardnessGroup = null;
        List<Mineral> actualIORGroup = null;
        List<Mineral> actualCrystalGroup = null;
        List<Mineral> actualCleavageGroup = null;
        List<Mineral> actualDefaultGroup = null;
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m2);
            testTable.addEntry(m3);
            testTable.addEntry(m4);
            testTable.addEntry(m5);
        } catch (DuplicationException e) {
            fail();
        }
        List<WikiEntry> expectedDensityGroup = new ArrayList<>(Arrays.asList(m4, m1, m2, m3, m5));
        List<WikiEntry> expectedHardnessGroup = new ArrayList<>(Arrays.asList(m5, m4, m1, m3, m2));
        List<WikiEntry> expectedIORGroup = new ArrayList<>(Arrays.asList(m5, m4, m1, m3, m2));
        List<WikiEntry> expectedCrystalGroup = new ArrayList<>(Arrays.asList(m2, m1, m4, m5, m3));
        List<WikiEntry> expectedCleavageGroup = new ArrayList<>(Arrays.asList(m2, m4, m5, m3, m1));
        List<WikiEntry> expectedDefaultGroup = new ArrayList<>(Arrays.asList(m1, m2, m3, m4, m5));

        try {
            actualDensityGroup = testTable.getTableSortedBy(Attributes.DENSITY);
            actualHardnessGroup = testTable.getTableSortedBy(Attributes.HARDNESS);
            actualIORGroup = testTable.getTableSortedBy(Attributes.IOR);
            actualCrystalGroup = testTable.getTableSortedBy(Attributes.CRYSTAL);
            actualCleavageGroup = testTable.getTableSortedBy(Attributes.CLEAVAGE);
            actualDefaultGroup = testTable.getTableSortedBy(Attributes.DEFAULT);
        } catch (EmptyTableException e) {
            fail();
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(expectedDensityGroup.get(i), actualDensityGroup.get(i));
            assertEquals(expectedHardnessGroup.get(i), actualHardnessGroup.get(i));
            assertEquals(expectedIORGroup.get(i), actualIORGroup.get(i));
            assertEquals(expectedCrystalGroup.get(i), actualCrystalGroup.get(i));
            assertEquals(expectedCleavageGroup.get(i), actualCleavageGroup.get(i));
            assertTrue(actualDefaultGroup.contains(expectedDefaultGroup.get(i)));
        }
    }

    @Test
    void testGetElementsSortedEmptyTable() {
        try {
            testTable.getTableSortedBy(Attributes.CLEAVAGE);
            fail();
        } catch (EmptyTableException e) {
            // Expected
        }
    }
    @Test
    void testToJson() {
        try {
            testTable.addEntry(m1);
            testTable.addEntry(m2);
            testTable.addEntry(m3);
            testTable.addEntry(m4);
            testTable.addEntry(m5);
        } catch (DuplicationException e) {
            fail();
        }
        System.out.println(testTable.toJson().toString());
    }
}