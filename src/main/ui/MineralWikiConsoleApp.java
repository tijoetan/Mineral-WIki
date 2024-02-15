package ui;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.enums.CrystalStructure;
import model.exceptions.*;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MineralWikiConsoleApp {
    final Scanner scanner;
    final MineralTable mineralTable;
    final FamilyTable familyTable;
    Boolean running;

    public MineralWikiConsoleApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.mineralTable = new MineralTable();
        this.familyTable = new FamilyTable();
    }

    public void runApp() {
        while (this.running) {
            String question = "What would you like to do\n"
                    + "a: add item\n"
                    + "d: delete item\n"
                    + "e: edit item\n"
                    + "g: view table\n"
                    + "v: view item \n"
                    + "q: quit";
            System.out.println(question);
            handleCommands(scanner.nextLine());
        }
    }

    public void handleCommands(String input) {
        switch (input.toLowerCase()) {
            case "a":
                addItem();
                break;
            case "d":
                deleteItem();
                break;
            case "e":
                editItem();
                break;
            case "g":
                viewTable();
                break;
            case "q":
                quit();
                break;
            case "v":
                viewItem();
                break;
            default:
                System.out.println("Command not understood");
        }
    }

    private void editItem() {
        WikiEntryTable table = getCorrectTable();
        if (table == null) {
            return;
        }

        try {
            String requested = queryString("What would you like to edit?");
            WikiEntry target = table.getRequestedEntry(requested);
            if (table.equals(this.mineralTable)) {
                System.out.println("Leave entry blank to keep the same");
                setupUserMineral((Mineral) target);
            } else {
                System.out.println("Leave entry blank to keep the same");
                setupUserFamily((Family) target);
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find item");
        } catch (NonNumericValueGiven e) {
            System.out.println("Enter a numerical value");
        } catch (UnknownElementException e) {
            System.out.println("Unknown element");
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown crystal structure");
        }

    }

    public void viewTable() {
        String tableType = queryString("Would you like to view the \"mineral\" or \"family\" table");
        if (tableType.equalsIgnoreCase("mineral")) {
            viewMineralTable();
        } else if (tableType.equalsIgnoreCase("family")) {
            viewFamilyTable();
        } else {
            System.out.println("Unknown statement");
        }
        queryString("Type anything to continue");
    }

    private void viewFamilyTable() {
        for (WikiEntry family : this.familyTable.getFamilies()) {
            family.printAllAttributes();
        }
    }

    private void viewMineralTable() {
        try {
            Attributes groupAttributes = Attributes.valueOf(queryString(
                    "What would you like the table to be grouped on:\n"
                            + "1. Crystal Structure\n"
                            + "2. Cleavage\n"
                            + "3. Hardness\n"
                            + "4. Density\n"
                            + "5. Index of refraction").toUpperCase());
            List<Mineral> sortedMineralList = this.mineralTable.getTableSortedBy(groupAttributes);
            for (WikiEntry mineral : sortedMineralList) {
                mineral.printAllAttributes();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown attribute");
        } catch (EmptyTableException e) {
            System.out.println("Your mineral table is empty!");
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
        this.running = false;
    }

    private void deleteItem() {
        WikiEntryTable table = getCorrectTable();
        if (table == null) {
            return;
        }
        String removeQuestion = "What is the name of the item you would like to remove?";
        try {
            table.removeEntry(queryString(removeQuestion));
            System.out.println("Item removed");
        } catch (ItemNotFoundException e) {
            System.out.println("Could not delete item");
        }

    }

    public WikiEntryTable getCorrectTable() {
        String request = queryString("Would you like to perform the task on a \"mineral\" or \"family\"");
        if (request.equalsIgnoreCase("mineral")) {
            return this.mineralTable;
        } else if (request.equalsIgnoreCase("family")) {
            return this.familyTable;
        } else {
            return null;
        }
    }

    private void viewItem() {
        WikiEntryTable table = getCorrectTable();
        if (table == null) {
            return;
        }
        String desiredItem = queryString("What would you like to view");
        try {
            WikiEntry item = table.getRequestedEntry(desiredItem);
            item.printAllAttributes();
            item.printDescription();
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
        queryString("Type anything to continue");

    }


    // MODIFIES: this
    // EFFECTS: creates and adds new mineral or family to mainTable
    private void addItem() {
        String whatToAdd = queryString("Would you like to add a \"mineral\" or \"family\"");
        if (whatToAdd.equalsIgnoreCase("mineral")) {
            enterMineral();
        } else if (whatToAdd.equalsIgnoreCase("family")) {
            enterFamily();
        } else {
            System.out.println("Command not understood");
        }

    }

    private void enterFamily() {
        String name = queryString("What is the name of your mineral family");
        try {
            Family newFamily = setupUserFamily(new Family(name));
            familyTable.addEntry(newFamily);
        } catch (UnknownElementException e) {
            System.out.println("Could not resolve formula");
        } catch (DuplicationException e) {
            System.out.println("Mineral Family Already exists");
        }

    }

    private Family setupUserFamily(Family startFamily) throws UnknownElementException {
        List<WikiEntry> familyMinerals = queryFamilyMinerals();
        String description = queryString("Please enter a quick description");
        Formula familyFormula = new Formula(queryString("What is the chemical formula"));
        fillFamily(startFamily, familyFormula, familyMinerals, description);
        return startFamily;
    }

    private static void fillFamily(Family family,
                                   Formula familyFormula,
                                   List<WikiEntry> familyMinerals,
                                   String description) {

        family.setGeneralFormula(familyFormula);
        family.addMineralsWithFamily(familyMinerals);
        family.setDescription(description);
    }

    private List<WikiEntry> queryFamilyMinerals() {
        List<WikiEntry> familyMinerals = new ArrayList<>();
        boolean doneEntering = false;
        while (!doneEntering) {
            try {
                String request = queryString("What mineral would you like to link to this? (q to stop)");
                if (request.equalsIgnoreCase("q")) {
                    doneEntering = true;
                    continue;
                }
                familyMinerals.add(mineralTable.getRequestedEntry(request));
            } catch (ItemNotFoundException e) {
                System.out.println("Could not find mineral");
            }
        }
        return familyMinerals;
    }

    // MODIFIES: this
    // EFFECTS: creates user specified mineral and adds it to mainTable
    private void enterMineral() {
        try {
            String name = queryString("What is the name of your mineral?: ");
            Mineral unconfiguredMineral = setupUserMineral(new Mineral(name));
            this.mineralTable.addEntry(unconfiguredMineral);
            System.out.println("Finished Adding");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not Resolve Crystal Structure");
        } catch (DuplicationException e) {
            System.out.println("Mineral Already Present");
        } catch (NonNumericValueGiven e) {
            System.out.println("The question: '" + e.getMessage() + "' needs a numeric responds");
        } catch (UnknownElementException e) {
            System.out.println("Formula is not valid");
        }
    }

    private Mineral setupUserMineral(Mineral startMineral)
            throws UnknownElementException, NonNumericValueGiven, IllegalArgumentException {
        String description = queryString("Please enter a quick description about your mineral:");
        Formula formula = new Formula(queryString("What is the chemical formula?: "));
        CrystalStructure crystalStructure = CrystalStructure.valueOf(queryString(
                "What is the crystalline Structure?: ").toUpperCase());
        Float indexOfRefraction = queryFloat("What is the mineral index of refraction?: ");
        Float density = queryFloat("What is the mineral density?: ");
        Float hardness = queryFloat("What is the Mohs hardness of the mineral?: ");
        fillMineral(startMineral, formula, crystalStructure, hardness, density, indexOfRefraction, description);
        return startMineral;
    }

    private static void fillMineral(Mineral mineral,
                                    Formula formula,
                                    CrystalStructure crystalStructure,
                                    Float hardness,
                                    Float density,
                                    Float indexOfRefraction,
                                    String description) {
        mineral.setGeneralFormula(formula);
        mineral.setCrystalStructure(crystalStructure);
        mineral.setHardness(hardness);
        mineral.setDensity(density);
        mineral.setIndexOfRefraction(indexOfRefraction);
        mineral.setDescription(description);
    }

    // EFFECTS: returns user input for a specified query
    private String queryString(String userQuestion) {
        System.out.println(userQuestion);
        String nextLine = scanner.nextLine();
        if (nextLine.isEmpty()) {
            return "NA";
        }
        return nextLine;
    }

    private Float queryFloat(String userQuestion) throws NonNumericValueGiven {
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
