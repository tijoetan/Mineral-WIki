package model.chemicalstructure;

import model.enums.AtomicSymbols;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoleculeGroupTest {

    MoleculeGroup g1;
    MoleculeGroup g2;

    List<FormulaElement> g1Elements = new ArrayList<>();
    List<FormulaElement> g2Elements = new ArrayList<>();

    @BeforeEach
    void beforeEach() {

        g1Elements.add(new FormulaElement(AtomicSymbols.H, 2));
        g1Elements.add(new FormulaElement(AtomicSymbols.HF, 3));

        g2Elements.add(new FormulaElement(AtomicSymbols.DY, 4));

        g1 = new MoleculeGroup(5, g1Elements);
        g2 = new MoleculeGroup(2, g2Elements);
    }

    @Test
    void getAmount() {
        assertEquals(5, g1.getAmount());
        assertEquals(2, g2.getAmount());
    }

    @Test
    void getElements() {
        assertEquals(g1Elements, g1.getElements());
        assertEquals(g2Elements, g2.getElements());
    }
}