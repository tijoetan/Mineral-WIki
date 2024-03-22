package utils;

import utils.fieldnames.Constants;

public class WrapString {

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
            if (!(lastSpace > 0)) {
                lastSpace = maxLength - 1;
                String toTransfer = starter.substring(0, lastSpace);
                returnString.append(toTransfer).append("-").append(wrapVal);
            } else {
                String toTransfer = starter.substring(0, lastSpace);
                returnString.append(toTransfer).append(wrapVal);
            }
            starter.delete(0, lastSpace);
        }

        return returnString.toString();

    }
}
