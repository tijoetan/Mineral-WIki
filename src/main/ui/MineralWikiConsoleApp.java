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
import persistence.InvalidFileException;
import persistence.TableReader;
import persistence.TableWriter;
import ui.additionmenu.UserQuery;
import ui.uiexceptions.NonNumericValueGiven;
import utils.FillWikiEntry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static ui.additionmenu.UserQuery.queryFloat;
import static ui.additionmenu.UserQuery.queryString;

// Console application that interprets user commands to instructions on model classes

public class MineralWikiConsoleApp {
    private final Scanner scanner;
    private final MineralTable mineralTable;
    private final FamilyTable familyTable;
    private boolean running;

    // EFFECTS: Constructor for MineralWikiConsoleApp and runs the application
    public MineralWikiConsoleApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.mineralTable = new MineralTable();
        this.familyTable = new FamilyTable();
        runApp();
        new MineralWikiGuiApp(mineralTable);
        new MineralWikiGuiApp(familyTable);
    }

    // MODIFIES: this
    // EFFECTS: main loop that takes user inputs and updates and queries the tables
    public void runApp() {
        askToLoad();
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

    private void askToLoad() {
        String userResponse = queryString("Would you like to load the database from a file\n"
                        + "(y) Yes\n"
                        + "(n) No",
                scanner);
        if (userResponse.equalsIgnoreCase("y")) {
            showAllSavedFiles();
            String source = queryString("Type the database that you would like to load", scanner);
            TableReader reader = new TableReader("data/" + source + ".json", familyTable, mineralTable);
            try {
                reader.setupTables();
            } catch (IOException e) {
                System.out.println("Could not load file");
            } catch (InvalidFileException e) {
                System.out.println("Could not understand file");
            }
        }
    }

    private void showAllSavedFiles() {
        try (Stream<Path> fileStream = Files.walk(Paths.get("data/"))) {
            StringBuilder fileNames = new StringBuilder();
            fileStream
                    .filter(path -> path.toString().split("\\"
                            + FileSystems.getDefault().getSeparator()).length <= 2
                            && path.getFileName().toString().endsWith(".json"))
                    .forEach(path -> fileNames.append(path.getFileName().toString().split("\\.")[0])
                            .append("\n"));
            System.out.println(fileNames);
        } catch (IOException e) {
            System.out.println("Could not find files");
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
            String requested = UserQuery.queryString("What would you like to edit?", this.scanner);
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
        String tableType = queryString("Would you like to view the \"mineral\" or \"family\" table",
                this.scanner);
        if (tableType.equalsIgnoreCase("mineral")) {
            viewMineralTable();
        } else if (tableType.equalsIgnoreCase("family")) {
            viewFamilyTable();
        } else {
            System.out.println("Unknown statement");
        }
        queryString("Type anything to continue", this.scanner);
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
                            + "5. Index of refraction", this.scanner).toUpperCase());
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
        askToSave();
        System.out.println("Goodbye!");
        this.running = false;
    }

    // MODIFIES:
    private void askToSave() {
        String response = queryString("Would you like to save your current database?\n"
                        + "(y) yes\n"
                        + "(n) no",
                scanner);
        if (response.equalsIgnoreCase("y")) {
            String destination = queryString("What would you like to name your database?: ", scanner);
            TableWriter writer = new TableWriter("data/" + destination + ".json");
            try {
                writer.open();
                writer.writeToDestination(mineralTable, familyTable);
            } catch (FileNotFoundException e) {
                System.out.println("Could not find file");
            } finally {
                writer.close();
            }
        }

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
            table.removeEntry(queryString(removeQuestion, this.scanner));
            System.out.println("Item removed");
        } catch (ItemNotFoundException e) {
            System.out.println("Could not delete item");
        }

    }

    // EFFECTS: returns either family or mineral table based on the user input
    public WikiEntryTable getCorrectTable() {
        String request = queryString("Would you like to perform the task on a \"mineral\" or \"family\"",
                this.scanner);
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
        String desiredItem = queryString("What would you like to view", this.scanner);
        try {
            WikiEntry item = table.getRequestedEntry(desiredItem);
            System.out.println(item.giveAllAttributes());
            System.out.println(item.getDescription());
        } catch (ItemNotFoundException e) {
            System.out.println("Could not find entry");
        }
        queryString("Type anything to continue", this.scanner);

    }


    // MODIFIES: this
    // EFFECTS: creates and adds new mineral or family to mainTable
    public void addItem() {
        String whatToAdd = queryString("Would you like to add a \"mineral\" or \"family\"", this.scanner);
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
        String name = queryString("What is the name of your mineral family", this.scanner);
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
        String description = queryString("Please enter a quick description", this.scanner);
        Formula familyFormula = new Formula(queryString("What is the chemical formula", this.scanner));
        FillWikiEntry.fillFamily(
                startFamily,
                familyFormula,
                familyMinerals,
                description);
        return startFamily;
    }

    // EFFECTS: returns a list containing user specified mineral names that are part of a family

    public List<WikiEntry> queryFamilyMinerals() {
        List<WikiEntry> familyMinerals = new ArrayList<>();
        boolean doneEntering = false;
        while (!doneEntering) {
            try {
                String request = queryString("What mineral would you like to link to this? (q to stop)", this.scanner);
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
            String name = queryString("What is the name of your mineral?: ", this.scanner);
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
        String description = queryString("Please enter a quick description about your mineral:", this.scanner);
        Formula formula = new Formula(queryString("What is the chemical formula?: ", this.scanner));
        CrystalStructure crystalStructure = CrystalStructure.valueOf(queryString(
                "What is the crystalline Structure?: ", this.scanner).toUpperCase());
        Float indexOfRefraction = queryFloat("What is the mineral index of refraction?: ", this.scanner);
        Float density = queryFloat("What is the mineral density?: ", this.scanner);
        Float hardness = queryFloat("What is the Mohs hardness of the mineral?: ", this.scanner);
        FillWikiEntry.fillMineral(
                startMineral,
                formula,
                crystalStructure,
                hardness,
                density,
                indexOfRefraction,
                description);
        return startMineral;
    }

}
