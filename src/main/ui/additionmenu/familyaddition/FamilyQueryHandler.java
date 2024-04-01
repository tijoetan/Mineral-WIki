package ui.additionmenu.familyaddition;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import model.tableentry.WikiEntryTable;
import ui.uiexceptions.BlankNameException;
import ui.misc.UserQuery;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Manages family queries

public class FamilyQueryHandler {

    // EFFECTS: Makes FamilyAdditionPanel and returns the user specified Family
    public static Family queryAddFamily(WikiEntryTable mineralTable) {
        FamilyAdditionPanel panel = new FamilyAdditionPanel();
        try {
            return queryFamily(panel, "Specify your Family!", mineralTable, null);
        } catch (BlankNameException e) {
            UserQuery.showErrorMessage("Cannot Have blank name");
            return null;
        }
    }

    // EFFECTS: shows popup of panel with title and returns user specified family
    //          throws BlankNameException if Family name is not provided
    private static Family queryFamily(FamilyAdditionPanel panel,
                                      String title,
                                      WikiEntryTable mineralTable,
                                      Family family) throws BlankNameException {
        Family newFamily;
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null);
        if (userResponse == JOptionPane.OK_OPTION) {
            if (family == null) {
                if (panel.getFamilyName().isEmpty()) {
                    throw new BlankNameException();
                }
                newFamily = new Family(panel.getFamilyName());
            } else {
                newFamily = family;
            }
            populateFamilyFields(panel, newFamily, mineralTable);
            return newFamily;
        } else {
            return null;
        }

    }

    // EFFECTS: prompts addition configured with family attributes
    public static Family queryEditFamily(Family family, WikiEntryTable mineralTable) {
        FamilyAdditionPanel additionPanel = new FamilyAdditionPanel();
        additionPanel.configurePanelBy(family);
        try {
            return queryFamily(additionPanel, "Edit your Family!", mineralTable, family);
        } catch (BlankNameException e) {
            throw new IllegalStateException();
        }
    }

    // EFFECTS: fills newFamily with attributes in panel
    private static void populateFamilyFields(FamilyAdditionPanel panel,
                                             Family newFamily,
                                             WikiEntryTable mineralTable) {
        Formula formula;
        List<WikiEntry> descendants = new ArrayList<>();
        String description = panel.getDescription();

        try {
            formula = new Formula(panel.getFormula());
        } catch (UnknownElementException e) {
            UserQuery.showErrorMessage("Unknown Formula Defaulting to blank");
            formula = new Formula();
        }

        for (String related : panel.getDescendants()) {
            try {
                descendants.add(mineralTable.getRequestedEntry(related));
            } catch (ItemNotFoundException e) {
                UserQuery.showErrorMessage("Could not find mineral with name:\n" + related);
            }
        }

        Family.fillFamily(newFamily,
                formula,
                descendants,
                description);

    }

}
