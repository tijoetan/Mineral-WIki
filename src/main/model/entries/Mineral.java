package model.entries;

import model.enums.Cleavage;
import model.enums.CrystalStructure;

public class Mineral extends WikiEntry {
    private CrystalStructure crystalStructure;
    private Cleavage cleavage;
    private Float indexOfRefraction;
    private Float hardness;
    private Float density;


    public void setIndexOfRefraction(float indexOfRefraction) {
        this.indexOfRefraction = indexOfRefraction;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public void setDensity(float density) {
        this.density = density;
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

    public Mineral(String name) {
        super(name);
        this.cleavage = Cleavage.NA;
        this.crystalStructure = CrystalStructure.NA;

        this.hardness = null;
        this.density = null;
        this.indexOfRefraction = null;
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

    public void setCrystalStructure(CrystalStructure crystalStructure) {
        this.crystalStructure = crystalStructure;
    }

    public CrystalStructure getCrystalStructure() {
        return this.crystalStructure;
    }


    public Cleavage getCleavage() {
        return this.cleavage;
    }

    public void setCleavage(Cleavage cleavage) {
        this.cleavage = cleavage;
    }

}


