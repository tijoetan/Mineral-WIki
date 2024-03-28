package ui.additionmenu.mineraladdition;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.UnknownElementException;
import org.jetbrains.annotations.Nullable;
import utils.FillWikiEntry;
import utils.UserQuery;

import javax.swing.*;
import java.util.Arrays;

public class MineralQueryHandler {

    public static Mineral queryAddMineral() {
        MineralAdditionPanel panel = new MineralAdditionPanel();
        return queryMineral(panel, "Specify your mineral!");
    }

    private static Mineral queryMineral(MineralAdditionPanel panel, String title) {
        return queryMineral(panel, title, null);
    }

    @Nullable
    private static Mineral queryMineral(MineralAdditionPanel panel, String title, Mineral mineral) {
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel,
                title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (userResponse == JOptionPane.OK_OPTION) {
            if (mineral == null) {
                mineral = new Mineral(panel.getName());
            }
            return populateMineral(panel, mineral);
        } else {
            return null;
        }
    }

    public static Mineral queryEditMineral(Mineral mineral) {
        MineralAdditionPanel panel = new MineralAdditionPanel();
        System.out.println(Arrays.toString(mineral.giveAttributeAsObjects()));
        panel.setFields(mineral);
        return queryMineral(panel, "Make your Changes!", mineral);
//      System.out.println(Arrays.toString(mineral.giveAttributeAsObjects()));


    }

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
