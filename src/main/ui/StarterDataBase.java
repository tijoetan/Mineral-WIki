package ui;

import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import model.tableentry.WikiEntryTable;

import java.util.List;

public class StarterDataBase {
    static StarterDataBase dataBase;

    List<Mineral> minerals;
    List<Family> families;

    private StarterDataBase() {

    }

    public StarterDataBase getInstance() {
        if (dataBase == null) {
            dataBase = new StarterDataBase();
        }

        return dataBase;
    }

    // MODIFIES: table
    // EFFECTS: adds all minerals and families to table
    public static void addAllEntries(WikiEntryTable mineralTable, WikiEntryTable familyTable) {

    }

}
