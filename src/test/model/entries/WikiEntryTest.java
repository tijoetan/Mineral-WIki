package model.entries;

import model.chemicalstructure.Formula;
import exceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WikiEntryTest {

    WikiEntry family;
    WikiEntry mineral;

    @BeforeEach
    void beforeEach() {
        family = new Family("testFamily");
        mineral = new Mineral("testMineral");
    }

    @Test
    void testConstruction() {
        assertEquals("No description provided", family.getDescription());
        assertEquals("testFamily", family.getName());

        assertFalse(family.getGeneralFormula().isValidFormula());

        assertEquals("No description provided", mineral.getDescription());
        assertEquals("testMineral", mineral.getName());

        assertFalse(mineral.getGeneralFormula().isValidFormula());
    }

    @Test
    void testSetDescriptionValidDescription() {
        family.setDescription("family");
        assertEquals("family", family.getDescription());

        mineral.setDescription("mineral");
        assertEquals("mineral", mineral.getDescription());
    }

    @Test
    void testSetDescriptionEmptyDescription() {
        family.setDescription("");
        assertEquals("No description provided", family.getDescription());
    }

    @Test
    void testSetGeneralFormulaValidThenInvalidFormula() {
        try {
            Formula familyFormula = new Formula("F");
            Formula mineralFormula = new Formula("Mo");

            family.setGeneralFormula(familyFormula);
            assertEquals(familyFormula, family.getGeneralFormula());

            mineral.setGeneralFormula(mineralFormula);
            assertEquals(mineralFormula, mineral.getGeneralFormula());

            mineral.setGeneralFormula(new Formula());
            assertEquals(mineralFormula, mineral.generalFormula);

        } catch (UnknownElementException e) {
            fail();
        }
    }

}