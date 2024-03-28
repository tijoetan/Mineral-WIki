package ui.additionmenu.familyaddition;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.WikiEntry;
import model.modelexceptions.ItemNotFoundException;
import model.modelexceptions.UnknownElementException;
import model.tableentry.WikiEntryTable;
import utils.FillWikiEntry;
import utils.UserQuery;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FamilyQueryHandler {
    public static Family queryAddFamily(WikiEntryTable mineralTable) {
        FamilyAdditionPanel panel = new FamilyAdditionPanel();
        return queryFamily(panel, "Specify your Family!", mineralTable, null);
    }

    private static Family queryFamily(FamilyAdditionPanel panel,
                                      String title,
                                      WikiEntryTable mineralTable,
                                      Family family) {
        Family newFamily;
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null);
        if (userResponse == JOptionPane.OK_OPTION) {
            if (family == null) {
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

    public static Family queryEditFamily(Family family, WikiEntryTable mineralTable) {
        FamilyAdditionPanel additionPanel = new FamilyAdditionPanel();
        additionPanel.configurePanelBy(family);
        return queryFamily(additionPanel, "Edit your Family!", mineralTable, family);
    }

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

        FillWikiEntry.fillFamily(newFamily,
                formula,
                descendants,
                description);

    }

}
