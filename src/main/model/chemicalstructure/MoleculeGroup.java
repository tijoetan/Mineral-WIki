package model.chemicalstructure;

import java.util.List;

public class MoleculeGroup {
    private int amount;
    private List<FormulaElement> elements;

    public MoleculeGroup(int amount, List<FormulaElement> elements) {
        this.amount = amount;
        this.elements = elements;
    }

}
