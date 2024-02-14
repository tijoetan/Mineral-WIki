package ui;

import exceptions.*;
import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Attributes;
import model.enums.CrystalStructure;
import model.enums.EntryType;
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
    String input;


    public MineralWikiConsoleApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.mineralTable = new MineralTable();
        this.familyTable = new FamilyTable();
        this.runApp();
    }

    public void runApp() {
        while (this.running) {
            System.out.println("What would you like to do\n"
                    + "a: add item\n"
                    + "d: delete item\n"
                    + "e: edit item\n"
                    + "g: view table\n"
                    + "v: view item \n"
                    + "q: quit\n");
            input = scanner.nextLine();
            handleCommands(input);
        }
    }

    public void handleCommands(String input) {
        switch (input.toLowerCase()) {
            case "a":
                this.addItem();
                break;
            case "d":
                this.deleteItem();
                break;
            case "e":
                this.editItem();
                break;
            case "g":
                this.viewMineralTable();
                break;
            case "q":
                this.quit();
                break;
            case "v":
                this.viewItem();
                break;
            default:
                System.out.println("Command not understood");
                break;

        }
    }

    private void editItem() {
    }

    private void viewMineralTable() {
        try {
            Attributes groupAttributes = Attributes.valueOf(
                    queryString("What would you like the table to be grouped on:\n"
                            + "1. Crystal Structure\n"
                            + "2. Cleavage\n"
                            + "3. Hardness\n"
                            + "4. Density\n"
                            + "5. Index of refraction\n").toUpperCase());
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
        String removeType = queryString("Would you like to remove a "
                + "\"family\" or \"mineral\"?").toUpperCase();
        String removeQuestion = "What is the name of the item you would like to remove?";
        try {
            EntryType type = EntryType.valueOf(removeType);
            this.mineralTable.removeEntry(queryString(removeQuestion));
            System.out.println("Item removed");
        } catch (ItemNotFoundException e) {
            System.out.println("Could not delete item");
        } catch (IllegalArgumentException e) {
            System.out.println("Only families and minerals are supported");
        }

    }

    private void viewItem() {
        WikiEntryTable table;
        String request = queryString("Would you like to view a \"mineral\" or a \"family\"?");
        if (request.equalsIgnoreCase("mineral")) {
            table = this.mineralTable;
        } else if (request.equalsIgnoreCase("family")) {
            table = this.familyTable;
        } else {
            System.out.println("Not understood");
            return;
        }

        String desiredItem = queryString(String.format("What %s would you like to view", request));
        try {
            WikiEntry item = table.getRequestedEntry(desiredItem);
            item.printAllAttributes();
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }

    }

    private void viewFamily() {
        String request = queryString("What family would you like to view");
        try {
            WikiEntry item = this.familyTable.getRequestedEntry(request);
            item.printAllAttributes();
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
    }

    private void viewMineral() {
        String request = queryString("What mineral would you like to view");
        try {
            WikiEntry item = this.mineralTable.getRequestedEntry(request);
            item.printAllAttributes();
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
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
        List<WikiEntry> familyMinerals = queryFamilyMinerals();
        Family newFamily = new Family(name);
        try {
            Formula familyFormula = new Formula(queryString("What is the chemical formula"));
            newFamily.setGeneralFormula(familyFormula);
            newFamily.addMineralsWithFamily(familyMinerals);
            familyTable.addEntry(newFamily);
        } catch (UnknownElementException e) {
            System.out.println("Could not resolve formula");
        } catch (DuplicationException e) {
            System.out.println("Mineral Family Already exists");
        }

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
        String name = queryString("What is the name of your mineral?");
        try {
            Formula formula = new Formula(queryString("What is the chemical formula?"));
            CrystalStructure crystalStructure = CrystalStructure.valueOf(queryString(
                    "What is the crystalline Structure?").toUpperCase());
            float indexOfRefraction = queryFloat("What is the mineral index of refraction?");
            float density = queryFloat("What is the mineral density?");
            float hardness = queryFloat("What is the Mohs hardness of the mineral?");
            Mineral mineral = new Mineral(name);
            fillMineral(mineral, formula, crystalStructure, hardness, density, indexOfRefraction);
            this.mineralTable.addEntry(mineral);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not Resolve Crystal Structure");
        } catch (MineralDuplicateException e) {
            System.out.println("Identical Mineral Already Present");
        } catch (InputMismatchException e) {
            System.out.println("The question: '" + e.getMessage() + "' needs a numeric responds");
        } catch (UnknownElementException e) {
            System.out.println("Formula is not valid");
        } catch (DuplicationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fillMineral(Mineral mineral, Formula formula, CrystalStructure crystalStructure,
                                    float hardness, float density, float indexOfRefraction) {
        mineral.setGeneralFormula(formula);
        mineral.setCrystalStructure(crystalStructure);
        mineral.setHardness(hardness);
        mineral.setDensity(density);
        mineral.setIndexOfRefraction(indexOfRefraction);
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
