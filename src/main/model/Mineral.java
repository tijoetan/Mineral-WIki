package model;

import model.exceptions.UnknownCrystalStructure;

public class Mineral extends WikiEntry {
    private CrystalStructure crystalStructure;

    public Mineral(String name) {
        super(name);
        this.crystalStructure = CrystalStructure.NOT_APPLICABLE;
    }

    public void setCrystalStructure(String crystalStructureName)
            throws UnknownCrystalStructure {
        try {
            this.crystalStructure = CrystalStructure.valueOf(crystalStructureName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCrystalStructure();
        }
    }

    public CrystalStructure getCrystalStructure() {
        return this.crystalStructure;
    }
}
