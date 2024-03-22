package utils;

import org.junit.jupiter.api.Test;
import utils.fieldnames.Constants;

class WrapStringTest {
    @Test
    void testWrapping() {
        String startString =  "IIEIFJEIFJEIFJEIFJEI" +
                "then the quick brown fox jumps over the lazy dog";
        System.out.println(StringUtils.wrapString(startString, 10, Constants.WRAP_FOR_GUI));
        System.out.println(startString);

    }

    @Test
    void testCamelling() {
        System.out.println(StringUtils.getSentenceCase("AL"));
    }

}