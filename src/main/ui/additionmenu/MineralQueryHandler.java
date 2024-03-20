package ui.additionmenu;

import model.chemicalstructure.Formula;
import model.entries.Mineral;
import model.modelexceptions.UnknownElementException;
import utils.FillWikiEntry;

import javax.swing.*;
import java.awt.*;

public class MineralQueryHandler {
    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void queryMineral() {
        Formula formula;
        Float hardness;
        Float ior;
        Float density;



        MineralAdditionPanel panel = new MineralAdditionPanel();
        int userResponse = JOptionPane.showConfirmDialog(null,
                panel.hostPanel,
                "Specify your mineral and press OK to create!",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (userResponse == JOptionPane.OK_OPTION) {
            Mineral createdMineral = new Mineral(panel.getName());

            try {
                formula = new Formula(panel.getFormula());
                System.out.println(panel.getFormula());
            } catch (UnknownElementException e) {
                showErrorMessage("Could not Understand formula");
                formula = new Formula();
            }

            try {
                hardness = Float.parseFloat(panel.getHardness());
                ior = Float.parseFloat(panel.getIor());
                density = Float.parseFloat(panel.getDensity());
            } catch (NumberFormatException e) {
                showErrorMessage("Number not provided");
                hardness = 0f;
                ior = 0f;
                density = 0f;

            }

            //FillWikiEntry.fillMineral(createdMineral, panel.getFo);
        } else {
            System.out.println("Sorry");
        }
    }
}
