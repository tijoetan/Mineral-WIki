package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrystalStructureTest {

    @Test
    void testToString() {
        assertEquals("Cubic", CrystalStructure.CUBIC.toString());
        assertEquals("Hexagonal", CrystalStructure.HEXAGONAL.toString());
        assertEquals("?", CrystalStructure.NA.toString());
    }

    @Test
    void getLiteralString() {
        assertEquals("CUBIC", CrystalStructure.CUBIC.getLiteralString());
        assertEquals("HEXAGONAL", CrystalStructure.HEXAGONAL.getLiteralString());
        assertEquals("NA", CrystalStructure.NA.getLiteralString());
    }
}