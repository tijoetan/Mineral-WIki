package utils;

import org.junit.jupiter.api.Test;
import utils.fieldnames.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    @Test
    void testExistence() {
        StringUtils utils = new StringUtils();
    }

    @Test
    void testWrapping() {
        String startString = "the quick brown fox jumps over the lazy dog";
        assertEquals("the quick \nbrown fox \njumps over \nthe lazy \ndog",
                StringUtils.wrapString(startString, 10, Constants.WRAP_FOR_CONSOLE));
        assertEquals("the quick <br>brown fox <br>jumps over <br>the lazy <br>dog",
                StringUtils.wrapString(startString, 10, Constants.WRAP_FOR_GUI));

        String longString = "abcdefg";
        assertEquals("abcd-\nefg",
                StringUtils.wrapString(longString, 5, Constants.WRAP_FOR_CONSOLE));
        assertEquals("abcd-<br>efg",
                StringUtils.wrapString(longString, 5, Constants.WRAP_FOR_GUI));

    }

    @Test
    void testGetSentenceCase() {
        assertEquals("Abcdefg",StringUtils.getSentenceCase("ABCDEFG"));
        assertEquals("The Quick Brown Fox",
                StringUtils.getSentenceCase("THE QUICK bROWN FoX"));
        assertEquals("The Quick Brown Fox",
                StringUtils.getSentenceCase("THE QUICK bROWN FoX "));
    }

    @Test
    void testCorrectSubscript() {
        assertEquals("<sub>2</sub>", StringUtils.subscriptValue(2));
        assertEquals("<sub>10</sub>", StringUtils.subscriptValue(10));
    }

    @Test
    void testCorrectTrimming() {
        String startString = "abcde";
        assertEquals("abcde", StringUtils.trimTo(startString, 6));
        assertEquals("abcde", StringUtils.trimTo(startString, 5));
        assertEquals("a...", StringUtils.trimTo(startString, 4));
    }

}