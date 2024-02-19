package model.chemicalstructure;

import model.enums.AtomicSymbols;
import model.modelexceptions.UnknownElementException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormulaTest {

    Formula f1;
    Formula f2;
    Formula f3;
    Formula f4;
    Formula f5;

    @Test
    void testConstructionWithValidUnparsedFormula() {
        try {
            f2 = new Formula("CaO3");
            f3 = new Formula("(Al, B, C)4U7");
        } catch (UnknownElementException e) {
            fail("Valid Formula Was Given");
        }

    }

    @Test
    void testConstructionForDummyElement() {
        f1 = new Formula();
        assertFalse(f1.isValidFormula());
        assertTrue(f1.getMoleculeList().isEmpty());
        assertTrue(f1.getSubstitutableGroups().isEmpty());
        assertTrue(f1.getCovalentGroups().isEmpty());
    }


    @Test
    void testConstructionExpectsUnknownElement() {
        try {
            f1 = new Formula("Q"); // Invalid 1 char
            fail("Invalid Formula Given");
        } catch (UnknownElementException e) {
            // Expected
        }
        try {
            f1 = new Formula("(Ap)"); // Invalid 2 char
            fail("Invalid Formula Given");
        } catch (UnknownElementException e) {
            // Expected
        }
        try {
            f1 = new Formula("AlO3(B, Y)9(Z)"); // Valid and Invalid
            fail("Invalid Formula Given");
        } catch (UnknownElementException e) {
            // Expected
        }
    }

    @Test
    void testGetFormulaAsString() {
        try {
            f1 = new Formula("N");
            f2 = new Formula("NA");
            f3 = new Formula("AlO3");
        } catch (UnknownElementException e) {
            fail();
        }
        assertEquals("N", f1.getFormulaAsString());
        assertEquals("NA", f2.getFormulaAsString());
        assertEquals("AlO3", f3.getFormulaAsString());
    }

    @Test
    void isValidFormula() {
        try {
            f1 = new Formula("NA");
            f2 = new Formula("NO3(B, U)2");
            f3 = new Formula("ZrZn(Al, K, Rb)O3H2O");
        } catch (UnknownElementException e) {
            fail();
        }

        assertFalse(f1.isValidFormula());
        assertTrue(f2.isValidFormula());
        assertTrue(f3.isValidFormula());

    }

    @Test
    void testParseFormulaSingleElementSingleQuantity() {
        try {
            f1 = new Formula("Al"); // Single Element
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f1.getMoleculeList().size());
        assertEquals(AtomicSymbols.AL, f1.getMoleculeList().get(0).getSymbol());
        assertEquals(1, f1.getMoleculeList().get(0).getCount());
        assertTrue(f1.getCovalentGroups().isEmpty());
        assertTrue(f1.getSubstitutableGroups().isEmpty());

    }

    @Test
    void testParseFormulaSingleElementManyItems() {
        try {
            f2 = new Formula("Mg2"); // Single Element multiple quantity
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f2.getMoleculeList().size());
        assertEquals(AtomicSymbols.MG, f2.getMoleculeList().get(0).getSymbol());
        assertEquals(2, f2.getMoleculeList().get(0).getCount());
        assertTrue(f2.getCovalentGroups().isEmpty());
        assertTrue(f2.getSubstitutableGroups().isEmpty());
    }

    @Test
    void testParseFormulaMultipleElements() {
        try {
            f3 = new Formula("Mg2AlO3"); // Multiple Elements
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(3, f3.getMoleculeList().size());

        assertEquals(AtomicSymbols.MG, f3.getMoleculeList().get(0).getSymbol());
        assertEquals(2, f3.getMoleculeList().get(0).getCount());

        assertEquals(AtomicSymbols.AL, f3.getMoleculeList().get(1).getSymbol());
        assertEquals(1, f3.getMoleculeList().get(1).getCount());

        assertEquals(AtomicSymbols.O, f3.getMoleculeList().get(2).getSymbol());
        assertEquals(3, f3.getMoleculeList().get(2).getCount());

        assertTrue(f3.getCovalentGroups().isEmpty());
        assertTrue(f3.getSubstitutableGroups().isEmpty());
    }

    @Test
    void testParseFormulaSubstitutableStrings() {
        try {
            f4 = new Formula("(Li, Na, K, Rb)O3(O, Cl2)2"); // Substitutable Groups
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f4.getMoleculeList().size());

        assertEquals(AtomicSymbols.O, f4.getMoleculeList().get(0).getSymbol());
        assertEquals(3, f4.getMoleculeList().get(0).getCount());

        List<MoleculeGroup> f4Subgroups = f4.getSubstitutableGroups();
        assertEquals(2, f4Subgroups.size());

        MoleculeGroup firstSubgroup = f4Subgroups.get(0);
        assertEquals(1, firstSubgroup.getAmount());
        assertEquals(4, firstSubgroup.getElements().size());

        assertEquals(AtomicSymbols.LI, firstSubgroup.getElements().get(0).getSymbol());
        assertEquals(1, firstSubgroup.getElements().get(0).getCount());

        assertEquals(AtomicSymbols.NA, firstSubgroup.getElements().get(1).getSymbol());
        assertEquals(1, firstSubgroup.getElements().get(1).getCount());

        assertEquals(AtomicSymbols.K, firstSubgroup.getElements().get(2).getSymbol());
        assertEquals(1, firstSubgroup.getElements().get(2).getCount());

        assertEquals(AtomicSymbols.RB, firstSubgroup.getElements().get(3).getSymbol());
        assertEquals(1, firstSubgroup.getElements().get(3).getCount());

        MoleculeGroup secondGroup = f4Subgroups.get(1);
        assertEquals(2, secondGroup.getAmount());
        assertEquals(2, secondGroup.getElements().size());

        assertEquals(AtomicSymbols.O, secondGroup.getElements().get(0).getSymbol());
        assertEquals(1, secondGroup.getElements().get(0).getCount());

        assertEquals(AtomicSymbols.CL, secondGroup.getElements().get(1).getSymbol());
        assertEquals(2, secondGroup.getElements().get(1).getCount());

        assertTrue(f4.getCovalentGroups().isEmpty());

    }

    @Test
    void testParseFormulaCovalentGroup() {
        try {
            f5 = new Formula("(NH4)4(OH)(CO3)Cl"); // Covalent Ion Groups
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f5.getMoleculeList().size());
        assertEquals(AtomicSymbols.CL, f5.getMoleculeList().get(0).getSymbol());
        assertEquals(1, f5.getMoleculeList().get(0).getCount());

        List<MoleculeGroup> f5CovalentGroups = f5.getCovalentGroups();
        assertEquals(3, f5CovalentGroups.size());

        MoleculeGroup firstCovalentGroup = f5CovalentGroups.get(0);
        assertEquals(4, firstCovalentGroup.getAmount());
        assertEquals(2, firstCovalentGroup.getElements().size());

        assertEquals(AtomicSymbols.N, firstCovalentGroup.getElements().get(0).getSymbol());
        assertEquals(1, firstCovalentGroup.getElements().get(0).getCount());

        assertEquals(AtomicSymbols.H, firstCovalentGroup.getElements().get(1).getSymbol());
        assertEquals(4, firstCovalentGroup.getElements().get(1).getCount());

        MoleculeGroup secondCovalentGroup = f5CovalentGroups.get(1);
        assertEquals(1, secondCovalentGroup.getAmount());
        assertEquals(2, secondCovalentGroup.getElements().size());

        assertEquals(AtomicSymbols.O, secondCovalentGroup.getElements().get(0).getSymbol());
        assertEquals(1, secondCovalentGroup.getElements().get(0).getCount());

        assertEquals(AtomicSymbols.H, secondCovalentGroup.getElements().get(1).getSymbol());
        assertEquals(1, secondCovalentGroup.getElements().get(1).getCount());

        MoleculeGroup thirdCovalentGroup = f5CovalentGroups.get(2);
        assertEquals(1, thirdCovalentGroup.getAmount());
        assertEquals(2, thirdCovalentGroup.getElements().size());

        assertEquals(AtomicSymbols.C, thirdCovalentGroup.getElements().get(0).getSymbol());
        assertEquals(1, thirdCovalentGroup.getElements().get(0).getCount());

        assertEquals(AtomicSymbols.O, thirdCovalentGroup.getElements().get(1).getSymbol());
        assertEquals(3, thirdCovalentGroup.getElements().get(1).getCount());

        assertTrue(f5.getSubstitutableGroups().isEmpty());
    }


    @Test
    void testValidPackageElements() {
        f1 = new Formula();
        List<FormulaElement> testTargetList = new ArrayList<>();
        try {
            f1.packageElements("AlBC2", testTargetList);
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(3, testTargetList.size());

        assertEquals(AtomicSymbols.AL, testTargetList.get(0).getSymbol());
        assertEquals(1, testTargetList.get(0).getCount());

        assertEquals(AtomicSymbols.B, testTargetList.get(1).getSymbol());
        assertEquals(1, testTargetList.get(1).getCount());

        assertEquals(AtomicSymbols.C, testTargetList.get(2).getSymbol());
        assertEquals(2, testTargetList.get(2).getCount());

        try {
            f1.packageElements("DsEr", testTargetList);
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(5, testTargetList.size());

        assertEquals(AtomicSymbols.DS, testTargetList.get(3).getSymbol());
        assertEquals(1, testTargetList.get(3).getCount());

        assertEquals(AtomicSymbols.ER, testTargetList.get(4).getSymbol());
        assertEquals(1, testTargetList.get(4).getCount());
    }

    @Test
    void testFailedPackageElements() {
        List<FormulaElement> testMoleculeList = new ArrayList<>();
        f1 = new Formula();

        try {
            f1.packageElements("A", testMoleculeList);
            fail();
        } catch (UnknownElementException e) {
           // Expected
        }

    }

    @Test
    void testGetStringComponent() {
        f1 = new Formula();

        assertEquals("Misha", f1.getStringComponent("1Misha"));
        assertEquals("A", f1.getStringComponent("1A7bcd"));
        assertEquals("", f1.getStringComponent(""));
        assertEquals("", f1.getStringComponent("123"));
    }

    @Test
    void testGetNumericalComponent() {
        f1 = new Formula();
        assertEquals(1, f1.getNumericalComponent("A1"));
        assertEquals(24, f1.getNumericalComponent("24O8"));
        assertEquals(1, f1.getNumericalComponent(""));
        assertEquals(1, f1.getNumericalComponent("ABCDE"));
    }

    @Test
    void testSuccessfulProcessSubGroup() {
        f1 = new Formula();

        try {
            MoleculeGroup substitutableGroup = f1.processSubGroup("(Ti2, V3)2");
            assertEquals(2, substitutableGroup.getAmount());
            assertEquals(2, substitutableGroup.getElements().size());

            assertEquals(AtomicSymbols.TI, substitutableGroup.getElements().get(0).getSymbol());
            assertEquals(2, substitutableGroup.getElements().get(0).getCount());

            assertEquals(AtomicSymbols.V, substitutableGroup.getElements().get(1).getSymbol());
            assertEquals(3 , substitutableGroup.getElements().get(1).getCount());
        } catch (UnknownElementException e) {
            fail();
        }

        try {
            MoleculeGroup covalentGroup = f1.processSubGroup("(Xe22PO3)12");
            assertEquals(12, covalentGroup.getAmount());
            assertEquals(3, covalentGroup.getElements().size());

            assertEquals(AtomicSymbols.XE, covalentGroup.getElements().get(0).getSymbol());
            assertEquals(22, covalentGroup.getElements().get(0).getCount());

            assertEquals(AtomicSymbols.P, covalentGroup.getElements().get(1).getSymbol());
            assertEquals(1, covalentGroup.getElements().get(1).getCount());

            assertEquals(AtomicSymbols.O, covalentGroup.getElements().get(2).getSymbol());
            assertEquals(3, covalentGroup.getElements().get(2).getCount());
        } catch (UnknownElementException e) {
            fail();
        }

    }

    @Test
    void testFailedProcessSubgroup() {
        f1 = new Formula();

        try {
            f1.processSubGroup("(Al, Be, Xp)");
            fail();
        } catch (UnknownElementException e) {
            // Expected
        }

        try {
            f1.processSubGroup("(COi)");
            fail();
        } catch (UnknownElementException e) {
            // Expected
        }

    }

}