package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleavageTest {

    @Test
    void testToString() {
        assertEquals("Cubic", Cleavage.CUBIC.toString());
        assertEquals("Octahedral", Cleavage.OCTAHEDRAL.toString());
        assertEquals("?", Cleavage.NA.toString());
    }

    @Test
    void getLiteralString() {
        assertEquals("CUBIC", Cleavage.CUBIC.getLiteralString());
        assertEquals("OCTAHEDRAL", Cleavage.OCTAHEDRAL.getLiteralString());
        assertEquals("NA", Cleavage.NA.getLiteralString());
    }
}