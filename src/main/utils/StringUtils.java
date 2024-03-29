package utils;

import utils.fieldnames.Constants;

// Various static methods involving Strings

public class StringUtils {

    // EFFECTS: Capitalizes the first letter of every string
    public static String getSentenceCase(String startString) {
        StringBuilder returnString = new StringBuilder();
        for (String word : startString.split(" ")) {
            returnString.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return returnString.substring(0, returnString.length() - 1);
    }

    // EFFECTS: Wraps String to desired length by placing line breaks in either standard or HTML format
    //          after the last space. If word exceeds line limit, splits word using "-" as delimiter
    public static String wrapString(String startString, int maxLength, int wrapType) {
        String wrapVal = wrapType == Constants.WRAP_FOR_GUI ? "<br>" : "\n";
        StringBuilder starter = new StringBuilder(startString);
        StringBuilder returnString = new StringBuilder();
        while (true) {
            if (starter.length() <= maxLength) {
                returnString.append(starter);
                break;
            }
            int lastSpace = starter.substring(0, maxLength + 1).lastIndexOf(" ") + 1;
            if (lastSpace <= 0) {
                lastSpace = maxLength - 1;
                String toTransfer = starter.substring(0, lastSpace);
                returnString.append(toTransfer).append("-").append(wrapVal);
            } else {
                String toTransfer = starter.substring(0, lastSpace);
                returnString.append(toTransfer).append(wrapVal);
            }
            starter.delete(0, lastSpace);
        }

//        System.out.println(startString);
        return returnString.toString();

    }

    // EFFECTS: Converts number into HTML subscript format
    public static String subscriptValue(int value) {
        return value == 1 ? "" :
                "<sub>" + value + "</sub>";
    }

    // EFFECTS: Trims text to given length by removing characters past the length limit and adding "..."
    public static String trimTo(String text, int length) {
        if (text.length() <= length) {
            return text;
        }

        return text.substring(0, length - 2) + "...";
    }

    // EFFECTS: None
    public StringUtils() {

    }
}
