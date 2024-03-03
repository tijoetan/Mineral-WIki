package ui;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.CrystalStructure;

import java.util.List;

public class FillWikiEntry {
    // MODIFIES: family
    // EFFECTS: calls appropriate setter commands on family for its attributes
    public static void fillFamily(Family family,
                                  Formula familyFormula,
                                  List<WikiEntry> familyMinerals,
                                  String description) {

        try {
            family.setGeneralFormula(familyFormula);
            family.addMineralsWithFamily(familyMinerals);
            family.setDescription(description);
        } catch (NullPointerException e) {
            //
        }
    }

    // MODIFIES: mineral
    // EFFECTS: calls appropriate mineral setter methods with the given parameters
    public static void fillMineral(Mineral mineral,
                                   Formula formula,
                                   CrystalStructure crystalStructure,
                                   Float hardness,
                                   Float density,
                                   Float indexOfRefraction,
                                   String description) {

        try {
            mineral.setGeneralFormula(formula);
            mineral.setCrystalStructure(crystalStructure);
            mineral.setHardness(hardness);
            mineral.setDensity(density);
            mineral.setIndexOfRefraction(indexOfRefraction);
            mineral.setDescription(description);
        } catch (NullPointerException e) {
            //
        }
    }
}
