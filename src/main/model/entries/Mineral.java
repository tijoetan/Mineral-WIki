package model.entries;

import model.enums.Cleavage;
import model.enums.CrystalStructure;

public class Mineral extends WikiEntry {
    private CrystalStructure crystalStructure;
    private Cleavage cleavage;
    private Float indexOfRefraction;
    private Float hardness;
    private Float density;


    public Mineral(String name) {
        super(name);
        this.cleavage = Cleavage.NA;
        this.crystalStructure = CrystalStructure.NA;

        this.hardness = 0.0f;
        this.density = 0.0f;
        this.indexOfRefraction = 0.0f;
    }

    public float getIndexOfRefraction() {
        return indexOfRefraction;
    }

    public float getHardness() {
        return hardness;
    }

    public float getDensity() {
        return density;
    }

    public CrystalStructure getCrystalStructure() {
        return this.crystalStructure;
    }


    public Cleavage getCleavage() {
        return this.cleavage;
    }

    public void setIndexOfRefraction(Float indexOfRefraction) {
        if (indexOfRefraction > 0) {
            this.indexOfRefraction = indexOfRefraction;
        }
    }

    public void setHardness(Float hardness) {
        if (hardness > 0) {
            this.hardness = hardness;
        }
    }

    public void setDensity(Float density) {
        if (density > 0) {
            this.density = density;
        }
    }

    public void setCrystalStructure(CrystalStructure crystalStructure) {
        if (crystalStructure != CrystalStructure.NA) {
            this.crystalStructure = crystalStructure;
        }
    }

    public void setCleavage(Cleavage cleavage) {
        if (cleavage != Cleavage.NA) {
            this.cleavage = cleavage;
        }
    }

    @Override
    public void printAllAttributes() {
        String attributeRow =
                String.format("Name: %s | Crystal Structure: %s | Formula: %s "
                                + "| Hardness: %s | Density: %s | IOR: %s | Cleavage %s",
                        this.name,
                        this.crystalStructure,
                        this.generalFormula.getFormulaAsString(),
                        this.hardness,
                        this.density,
                        this.indexOfRefraction,
                        this.cleavage);
        System.out.println(attributeRow);
    }

}


