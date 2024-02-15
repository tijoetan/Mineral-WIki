package model.chemicalstructure;

import java.util.List;

public class MoleculeGroup {
    private final int amount;
    private final List<FormulaElement> elements;

    //EFFECTS: Initializes MoleculeGroup with amount and elements
    public MoleculeGroup(int amount, List<FormulaElement> elements) {
        this.amount = amount;
        this.elements = elements;
    }

    public int getAmount() {
        return amount;
    }

    public List<FormulaElement> getElements() {
        return elements;
    }

}
