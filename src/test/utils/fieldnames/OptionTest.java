package utils.fieldnames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {
    @Test
    void testOption() {
        assertNotNull(Option.FAMILY);
        assertNotNull(Option.MINERAL);
    }

}