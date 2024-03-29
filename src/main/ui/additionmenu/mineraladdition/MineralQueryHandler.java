package ui.additionmenu.mineraladdition;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.UnknownElementException;
import ui.uiexceptions.BlankNameException;
import utils.FillWikiEntry;
import ui.misc.UserQuery;

import javax.swing.*;
import java.util.Arrays;

public class MineralQueryHandler {

    // EFFECTS: produces user specified Mineral
    public static Mineral queryAddMineral() {
        MineralAdditionPanel panel = new MineralAdditionPanel();
        return queryMineral(panel, "Specify your mineral!");
    }

    // EFFECTS: produces on given title popup with panel
    private static Mineral queryMineral(MineralAdditionPanel panel, String title) {
        try {
            return queryMineral(panel, title, null);
        } catch (BlankNameException e) {
            UserQuery.showErrorMessage("Cannot Have Blank Name");
            return null;
        }
    }

    // EFFECTS: produces popup with given popup and title with fields initialized to mineral
    private static Mineral queryMineral(MineralAdditionPanel panel, String title, Mineral mineral)
            throws BlankNameException {
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (userResponse == JOptionPane.OK_OPTION) {
            if (mineral == null) {
                if (panel.getName().isEmpty()) {
                    throw new BlankNameException();
                }
                mineral = new Mineral(panel.getName());
            }
            return populateMineral(panel, mineral);
        } else {
            return null;
        }
    }

    // EFFECTS: edits mineral
    public static Mineral queryEditMineral(Mineral mineral) {
        MineralAdditionPanel panel = new MineralAdditionPanel();
        System.out.println(Arrays.toString(mineral.giveAttributeAsObjects()));
        panel.setFields(mineral);
        try {
            return queryMineral(panel, "Make your Changes!", mineral);
        } catch (BlankNameException e) {
            throw new IllegalStateException();
        }
//      System.out.println(Arrays.toString(mineral.giveAttributeAsObjects()));


    }

    // EFFECTS: fills mineral based on user specification on panel
    private static Mineral populateMineral(MineralAdditionPanel panel, Mineral mineral) {
        float ior;
        float density;
        Formula formula;
        float hardness;
        formula = getFormula(panel);

        try {
            hardness = Float.parseFloat(panel.getHardness());
            ior = Float.parseFloat(panel.getIor());
            density = Float.parseFloat(panel.getDensity());
        } catch (NumberFormatException e) {
            UserQuery.showErrorMessage("Number not provided \n Defaulting to 0");
            hardness = 0f;
            ior = 0f;
            density = 0f;
        }

        FillWikiEntry.fillMineral(mineral,
                formula,
                (CrystalStructure) panel.getCrystalStructure(),
                hardness, density, ior,
                panel.getDescription(),
                (Cleavage) panel.getCleavage());


        return mineral;
    }

    // EFFECTS: produces formula based on panel formula field
    private static Formula getFormula(MineralAdditionPanel panel) {
        Formula formula;
        try {
            formula = new Formula(panel.getFormula());
            System.out.println(panel.getFormula());
        } catch (UnknownElementException e) {
            UserQuery.showErrorMessage("Could not Understand formula \n Defaulting to blank formula");
            formula = new Formula();
        }
        return formula;
    }
}
