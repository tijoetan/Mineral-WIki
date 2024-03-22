package ui.additionmenu;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.UnknownElementException;
import utils.FillWikiEntry;

import javax.swing.*;

public class MineralQueryHandler {
    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static Mineral queryMineral() {
        MineralAdditionPanel panel = new MineralAdditionPanel();
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel,
                "Specify your mineral!",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (userResponse == JOptionPane.OK_OPTION) {
            return populateMineral(panel);
        } else {
            System.out.println("Sorry");
            return null;
        }
    }

    private static Mineral populateMineral(MineralAdditionPanel panel) {
        float ior;
        float density;
        Formula formula;
        float hardness;
        Mineral createdMineral = new Mineral(panel.getName());
        formula = getFormula(panel);

        try {
            hardness = Float.parseFloat(panel.getHardness());
            ior = Float.parseFloat(panel.getIor());
            density = Float.parseFloat(panel.getDensity());
        } catch (NumberFormatException e) {
            showErrorMessage("Number not provided \n Defaulting to 0");
            hardness = 0f;
            ior = 0f;
            density = 0f;
        }

        FillWikiEntry.fillMineral(createdMineral,
                formula,
                (CrystalStructure) panel.getCrystalStructure(),
                hardness, density, ior,
                panel.getDescription(),
                (Cleavage) panel.getCleavage());

        return createdMineral;
    }

    private static Formula getFormula(MineralAdditionPanel panel) {
        Formula formula;
        try {
            formula = new Formula(panel.getFormula());
            System.out.println(panel.getFormula());
        } catch (UnknownElementException e) {
            showErrorMessage("Could not Understand formula \n Defaulting to blank formula");
            formula = new Formula();
        }
        return formula;
    }
}
