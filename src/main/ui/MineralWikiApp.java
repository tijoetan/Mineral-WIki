package ui;

import model.EntryTable;
import model.enums.Attributes;
import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.CrystalStructure;
import model.enums.EntryType;
import model.exceptions.ItemNotFoundException;
import model.exceptions.MineralDuplicateException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MineralWikiApp {
    Boolean running;
    Scanner scanner;
    String input;
    EntryTable mainTable;

    public MineralWikiApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.mainTable = new EntryTable();
        this.runApp();
    }


    public void runApp() {
        while (this.running) {
            System.out.println("What would you like to do: ");
            input = scanner.nextLine();
            handleCommands(input);

        }
    }

    public void handleCommands(String input) {
        if (input.length() == 1) {
            switch (input.toLowerCase().charAt(0)) {
                case 'a':
                    this.addItem();
                    break;
                case 'v':
                    this.viewItem();
                    break;
                case 'd':
                    this.deleteItem();
                    break;
                case 'q':
                    this.quit();
                    break;
                case 'g':
                    this.viewMineralTable();
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Command not understood");
        }
    }

    private void viewMineralTable() {

        try {
            Attributes groupAttributes = Attributes.valueOf(
                    queryString("What would you like the table to be grouped on").toUpperCase());
            List<Mineral> sortedMineralList = this.mainTable.getMineralTableSortedBy(groupAttributes);
            for (Mineral mineral : sortedMineralList) {
                mineral.printAllAttributes();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown Table to sort by");
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
        this.running = false;
    }

    private void deleteItem() {
        String removeType = queryString("Would you like to remove a family or mineral?").toUpperCase();
        String removeQuestion = "What is the name of the item you would like to remove?";
        try {
            EntryType type = EntryType.valueOf(removeType);
            this.mainTable.removeEntry(queryString(removeQuestion), type);
            System.out.println("Item removed");
        } catch (ItemNotFoundException e) {
            System.out.println("Could not delete item");
        } catch (IllegalArgumentException e) {
            System.out.println("Only families and minerals are supported");
        }

    }

    private void viewItem() {
        String request = queryString("What entry would you like to view");
        try {
            WikiEntry item = this.mainTable.getRequestedEntry(request);
            item.printAllAttributes();
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and adds new mineral or family to mainTable
    private void addItem() {
        String whatToAdd = queryString("Would you like to add a mineral or family");
        if (whatToAdd.equalsIgnoreCase("mineral")) {
            enterMineral();
        } else if (whatToAdd.equalsIgnoreCase("family")) {
            enterFamily();
        } else {
            System.out.println("Command not understood");
        }

    }

    private void enterFamily() {

    }

    // MODIFIES: this
    // EFFECTS: creates user specified mineral and adds it to mainTable
    private void enterMineral() {
        Formula formula = new Formula();
        String name = queryString("What is the name of your mineral?");
        try {
            CrystalStructure crystalStructure = CrystalStructure.valueOf(queryString(
                    "What is the crystalline Structure?").toUpperCase());
            float indexOfRefraction = queryFloat("What is the mineral index of refraction?");
            float density = queryFloat("What is the mineral density?");
            float hardness = queryFloat("What is the Mohs hardness of the mineral?");
            fillFormulaConstituents(formula);
            Mineral mineral = new Mineral(name);
            mineral.setGeneralFormula(formula);
            mineral.setCrystalStructure(crystalStructure);
            mineral.setHardness(hardness);
            mineral.setDensity(density);
            mineral.setIndexOfRefraction(indexOfRefraction);
            this.mainTable.addEntry(mineral, EntryType.MINERAL);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not Resolve Crystal Structure");
        } catch (MineralDuplicateException e) {
            System.out.println("Identical Mineral Already Present");
        } catch (InputMismatchException e) {
            System.out.println("The question: '" + e.getMessage() + "' needs a numeric responds");
        }

    }

    // MODIFIES: formula
    // EFFECTS: adds user inputs to formula unit entries
    private void fillFormulaConstituents(Formula formula) {
        boolean finishedEnteringFormula = false;
        String currentFormulaUnit;
        while (!finishedEnteringFormula) {
            System.out.println("What makes up the formula (type q to stop)");
            currentFormulaUnit = scanner.nextLine();
            if (currentFormulaUnit.equals("q")) {
                finishedEnteringFormula = true;
            } else {
                formula.addConstituent(currentFormulaUnit);
            }
        }
    }

    // EFFECTS: returns user input for a specified query
    private String queryString(String userQuestion) {
        System.out.println(userQuestion);
        return scanner.nextLine();
    }

    private float queryFloat(String userQuestion) throws InputMismatchException {
        System.out.println(userQuestion);
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            throw new InputMismatchException(userQuestion);
        }
    }

}
