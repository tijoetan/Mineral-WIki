package ui;

import chemicalstructure.Formula;
import model.Mineral;
import model.MineralTable;
import model.exceptions.MineralDuplicateException;
import model.exceptions.UnknownCrystalStructure;

import java.util.Scanner;

public class MineralWikiApp {
    Boolean running;
    Scanner scanner;
    String input;
    MineralTable table;

    public MineralWikiApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.table = new MineralTable();
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
                default:
                    break;
            }
        } else {
            System.out.println("Command not understood");
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
        this.running = false;
    }

    private void deleteItem() {
        System.out.println("Deleting Mineral");
    }

    private void viewItem() {
        System.out.println("Viewing Mineral");
    }

    @SuppressWarnings({"checkstyle:methodlength", "checkstyle:SuppressWarnings"})
    private void addItem() {
        String currentFormulaUnit;
        Formula formula = new Formula();
        boolean finishedEnteringFormula = false;
        System.out.println("What is the name of your mineral");
        String name = scanner.nextLine();
        System.out.println("What is the crystalline Structure");
        String crystalStructureName = scanner.nextLine();
        while (!finishedEnteringFormula) {
            System.out.println("What makes up the formula (type q to stop)");
            currentFormulaUnit = scanner.nextLine();
            if (currentFormulaUnit.equals("q")) {
                finishedEnteringFormula = true;
            } else {
                formula.addConstituent(currentFormulaUnit);
            }
        }
        try {
            Mineral mineral = new Mineral(name);
            mineral.setGeneralFormula(formula);
            mineral.setCrystalStructure(crystalStructureName);
            this.table.addMineral(mineral);
        } catch (MineralDuplicateException e) {
            System.out.println("Identical Mineral Already Present");
        } catch (UnknownCrystalStructure e) {
            System.out.println("Could not Resolve Crystal Structure");
        }

    }

}
