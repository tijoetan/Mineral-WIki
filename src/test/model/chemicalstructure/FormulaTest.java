package model.chemicalstructure;

import model.enums.AtomicSymbols;
import model.exceptions.UnknownElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormulaTest {

    Formula f1;
    Formula f2;
    Formula f3;
    Formula f4;
    Formula f5;
    Formula f6;

    @Test
    void testConstructionWithValidUnparsedFormula() {
        try {
            f1 = new Formula("NA");
            f2 = new Formula("CaO3");
            f3 = new Formula("(Al, B, C)4U7");
        } catch (UnknownElementException e) {
            fail("Valid Formula Was Given");
        }

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
            f2 = new Formula("Mg2"); // Single Element multiple quantity
            f3 = new Formula("Mg2AlO3"); // Multiple Elements
            f4 = new Formula("(Li, Na, K, Rb)O3(F, Cl)2"); // Substitutable Groups
            f5 = new Formula("(NH4)4(OH)(CO3)Cl"); // Covalent Ion Groups
            f6 = new Formula("(Mg, Ba)2BeAl2(CO3)2(H2O)5"); // All
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f1.getMoleculeList().size());
        assertEquals(AtomicSymbols.AL, f1.getMoleculeList().get(0).getSymbol());
        assertEquals(1 , f1.getMoleculeList().get(0).getCount());
        assertTrue(f1.getCovalentGroups().isEmpty());
        assertTrue(f1.getSubstitutableGroups().isEmpty());

    }

    @Test
    void testParseFormulaSingleElementManyItems() {
        try {
            f2 = new Formula("Mg2"); // Single Element multiple quantity
            f3 = new Formula("Mg2AlO3"); // Multiple Elements
            f4 = new Formula("(Li, Na, K, Rb)O3(F, Cl)2"); // Substitutable Groups
            f5 = new Formula("(NH4)4(OH)(CO3)Cl"); // Covalent Ion Groups
            f6 = new Formula("(Mg, Ba)2BeAl2(CO3)2(H2O)5"); // All
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(1, f2.getMoleculeList().size());
        assertEquals(AtomicSymbols.MG, f2.getMoleculeList().get(0).getSymbol());
        assertEquals(2 , f2.getMoleculeList().get(0).getCount());
        assertTrue(f2.getCovalentGroups().isEmpty());
        assertTrue(f2.getSubstitutableGroups().isEmpty());
    }

    @Test
    void testParseFormulaMultipleElements() {
        try {
            f3 = new Formula("Mg2AlO3"); // Multiple Elements
            f4 = new Formula("(Li, Na, K, Rb)O3(F, Cl)2"); // Substitutable Groups
            f5 = new Formula("(NH4)4(OH)(CO3)Cl"); // Covalent Ion Groups
            f6 = new Formula("(Mg, Ba)2BeAl2(CO3)2(H2O)5"); // All
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(3, f3.getMoleculeList().size());

        assertEquals(AtomicSymbols.MG, f3.getMoleculeList().get(0).getSymbol());
        assertEquals(2 , f3.getMoleculeList().get(0).getCount());

        assertEquals(AtomicSymbols.AL, f3.getMoleculeList().get(1).getSymbol());
        assertEquals(1 , f3.getMoleculeList().get(1).getCount());

        assertEquals(AtomicSymbols.O, f3.getMoleculeList().get(2).getSymbol());
        assertEquals(3 , f3.getMoleculeList().get(2).getCount());

        assertTrue(f3.getCovalentGroups().isEmpty());
        assertTrue(f3.getSubstitutableGroups().isEmpty());
    }

    @Test
    void testParseFormulaSubstitutableStrings() {
        try {
            f3 = new Formula("Mg2AlO3"); // Multiple Elements
            f4 = new Formula("(Li, Na, K, Rb)O3(F, Cl)2"); // Substitutable Groups
            f5 = new Formula("(NH4)4(OH)(CO3)Cl"); // Covalent Ion Groups
            f6 = new Formula("(Mg, Ba)2BeAl2(CO3)2(H2O)5"); // All
        } catch (UnknownElementException e) {
            fail();
        }

        assertEquals(3, f3.getMoleculeList().size());

        assertEquals(AtomicSymbols.MG, f3.getMoleculeList().get(0).getSymbol());
        assertEquals(2 , f3.getMoleculeList().get(0).getCount());

        assertEquals(AtomicSymbols.AL, f3.getMoleculeList().get(1).getSymbol());
        assertEquals(1 , f3.getMoleculeList().get(1).getCount());

        assertEquals(AtomicSymbols.O, f3.getMoleculeList().get(2).getSymbol());
        assertEquals(3 , f3.getMoleculeList().get(2).getCount());

        assertTrue(f3.getCovalentGroups().isEmpty());
        assertTrue(f3.getSubstitutableGroups().isEmpty());
    }



    @Test
    void packageElements() {
    }

    @Test
    void getStringComponent() {
    }

    @Test
    void getNumericalComponent() {
    }

    @Test
    void processSubGroup() {
    }
}