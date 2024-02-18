package model.chemicalstructure;

import enums.AtomicSymbols;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormulaElementTest {
    FormulaElement e1;
    FormulaElement e2;

    @BeforeEach
    void beforeEach() {
        e1 = new FormulaElement(AtomicSymbols.PR, 1);
        e2 = new FormulaElement(AtomicSymbols.RA, 2);
    }
    @Test
    void testGetCount() {
        assertEquals(1, e1.getCount());
        assertEquals(2, e2.getCount());
    }

    @Test
    void testGetSymbol() {
        assertEquals(AtomicSymbols.PR ,e1.getSymbol());
        assertEquals(AtomicSymbols.RA, e2.getSymbol());
    }
}