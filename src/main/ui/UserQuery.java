package ui;

import ui.uiexceptions.NonNumericValueGiven;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserQuery {
    // EFFECTS: returns user input for a specified query returns "NA" if entry is blank
    public static String queryString(String userQuestion, Scanner scanner) {
        System.out.println(userQuestion);
        String nextLine = scanner.nextLine();
        if (nextLine.isEmpty()) {
            return "NA";
        }
        return nextLine;
    }

    // EFFECTS: returns user input of float type.
    //          throws NonNumericValueGiven if input cannot be interpreted as a float
    public static Float queryFloat(String userQuestion, Scanner scanner) throws NonNumericValueGiven {
        try {
            System.out.println(userQuestion);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return -1.0f;
            }
            return Float.parseFloat(input);
        } catch (InputMismatchException e) {
            throw new NonNumericValueGiven(userQuestion);
        }
    }
}
