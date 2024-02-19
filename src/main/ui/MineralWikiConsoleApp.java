package ui;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.enums.CrystalStructure;
import model.modelexceptions.DuplicationException;
import model.modelexceptions.EmptyTableException;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;
import ui.uiexceptions.NonNumericValueGiven;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MineralWikiConsoleApp {
    private final Scanner scanner;
    private final MineralTable mineralTable;
    private final FamilyTable familyTable;
    private Boolean running;

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

    // MODIFIES: this
    // EFFECTS: performs operation based on input given
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

    // MODIFIES: this
    // EFFECTS: changes an entry in either mineralTable or familyTable
    public void editItem() {
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

    // EFFECTS: prints out the requested table with mineral attributes being sortable
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

    // EFFECTS: prints out family table
    public void viewFamilyTable() {
        for (WikiEntry family : this.familyTable.getFamilies()) {
            System.out.println(family.giveAllAttributes());
        }
    }

    // EFFECTS: prints out mineral table with entries sorted on user attribute
    public void viewMineralTable() {
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
                System.out.println(mineral.giveAllAttributes());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown attribute");
        } catch (EmptyTableException e) {
            System.out.println("Your mineral table is empty!");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets running to false and ends the program
    public void quit() {
        System.out.println("Goodbye!");
        this.running = false;
    }

    // MODIFIES: this
    // EFFECTS: removes user requested item either the mineral or family table
    public void deleteItem() {
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

    // EFFECTS: returns either family or mineral table based on the user input
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

    // EFFECTS: gets a user specified items and prints out all its attributes and its description
    public void viewItem() {
        WikiEntryTable table = getCorrectTable();
        if (table == null) {
            return;
        }
        String desiredItem = queryString("What would you like to view");
        try {
            WikiEntry item = table.getRequestedEntry(desiredItem);
            System.out.println(item.giveAllAttributes());
            System.out.println(item.getDescription());
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
        queryString("Type anything to continue");

    }


    // MODIFIES: this
    // EFFECTS: creates and adds new mineral or family to mainTable
    public void addItem() {
        String whatToAdd = queryString("Would you like to add a \"mineral\" or \"family\"");
        if (whatToAdd.equalsIgnoreCase("mineral")) {
            enterMineral();
        } else if (whatToAdd.equalsIgnoreCase("family")) {
            enterFamily();
        } else {
            System.out.println("Command not understood");
        }

    }

    // MODIFIES: this
    // EFFECTS: creates a new family with attributes specified by the user and adds it to familyTable
    public void enterFamily() {
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

    // MODIFIES: startFamily
    // EFFECTS: Takes a family and sets its attributes to the user's answers
    //          throws UnknownElementExceptions if given formula is not valid
    public Family setupUserFamily(Family startFamily) throws UnknownElementException {
        List<WikiEntry> familyMinerals = queryFamilyMinerals();
        String description = queryString("Please enter a quick description");
        Formula familyFormula = new Formula(queryString("What is the chemical formula"));
        fillFamily(startFamily, familyFormula, familyMinerals, description);
        return startFamily;
    }

    // MODIFIES: family
    // EFFECTS: calls appropriate setter commands on family for its attributes
    public static void fillFamily(Family family,
                                  Formula familyFormula,
                                  List<WikiEntry> familyMinerals,
                                  String description) {

        family.setGeneralFormula(familyFormula);
        family.addMineralsWithFamily(familyMinerals);
        family.setDescription(description);
    }

    // EFFECTS: returns a list containing user specified mineral names that are part of a family
    public List<WikiEntry> queryFamilyMinerals() {
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
    // EFFECTS: creates user specified mineral and adds it to mineralTable
    public void enterMineral() {
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


    // MODIFIES: startMineral
    // EFFECTS: populates fields of startMineral with user specified inputs.
    //          throws UnknownElementException if user formula is not valid
    //          throws NonNumericValueGiven if a numeric query does not receive a numeric response
    //          throws IllegalArgumentException if provided CrystalStructure is not in the Enum
    public Mineral setupUserMineral(Mineral startMineral)
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

    // MODIFIES: mineral
    // EFFECTS: calls appropriate mineral setter methods with the given parameters
    public static void fillMineral(Mineral mineral,
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

    // EFFECTS: returns user input for a specified query returns "NA" if entry is blank
    public String queryString(String userQuestion) {
        System.out.println(userQuestion);
        String nextLine = scanner.nextLine();
        if (nextLine.isEmpty()) {
            return "NA";
        }
        return nextLine;
    }

    // EFFECTS: returns user input of float type.
    //          throws NonNumericValueGiven if input cannot be interpreted as a float
    public Float queryFloat(String userQuestion) throws NonNumericValueGiven {
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
