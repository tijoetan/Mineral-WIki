package utils;

import model.chemicalstructure.Formula;
import model.entries.Family;
import model.entries.Mineral;
import model.entries.WikiEntry;
import model.enums.Cleavage;
import model.enums.CrystalStructure;

import java.util.List;

// Populates WikiEntries

public class FillWikiEntry {

    // EFFECTS: none
    public FillWikiEntry() {

    }

    // MODIFIES: family
    // EFFECTS: calls appropriate setter commands on family for its attributes
    public static void fillFamily(Family family,
                                  Formula familyFormula,
                                  List<WikiEntry> familyMinerals,
                                  String description) {

        family.setGeneralFormula(familyFormula);
        family.setMineralsWithFamily(familyMinerals);
        family.setDescription(description);

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

        mineral.setGeneralFormula(formula);
        mineral.setCrystalStructure(crystalStructure);
        mineral.setHardness(hardness);
        mineral.setDensity(density);
        mineral.setIndexOfRefraction(indexOfRefraction);
        mineral.setDescription(description);
    }

    // MODIFIES: mineral
    // EFFECTS: calls appropriate mineral setter methods with the given parameters
    public static void fillMineral(Mineral mineral,
                                   Formula formula,
                                   CrystalStructure crystalStructure,
                                   Float hardness,
                                   Float density,
                                   Float indexOfRefraction,
                                   String description,
                                   Cleavage cleavage) {

        mineral.setGeneralFormula(formula);
        mineral.setCrystalStructure(crystalStructure);
        mineral.setHardness(hardness);
        mineral.setDensity(density);
        mineral.setIndexOfRefraction(indexOfRefraction);
        mineral.setDescription(description);
        mineral.setCleavage(cleavage);
    }
}

