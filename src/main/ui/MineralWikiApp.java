package ui;

import java.util.Scanner;

public class MineralWikiApp {
    Boolean running;
    Scanner scanner;
    String input;

    public MineralWikiApp() {
        this.running = true;
        this.scanner = new Scanner(System.in);
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
            switch (input.toUpperCase().charAt(0)) {
                case 'A':
                    this.addMineral();
                    break;
                case 'V':
                    this.viewMineral();
                    break;
                case 'D':
                    this.deleteMineral();
                    break;
                case 'Q':
                    this.quit();
                    break;
                default:
                    System.out.println("Command not understood");
            }
        }
    }

    private void quit() {
        System.out.println("Goodbye!");
        this.running = false;
    }

    private void deleteMineral() {
        System.out.println("Deleting Mineral");
    }

    private void viewMineral() {
        System.out.println("Viewing Mineral");
    }

    private void addMineral() {
        System.out.println("Adding Mineral");
    }

}
