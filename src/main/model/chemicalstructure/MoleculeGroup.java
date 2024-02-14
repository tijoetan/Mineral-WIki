package model.chemicalstructure;

import java.util.List;

public class MoleculeGroup {
    private  int amount;
    private  List<FormulaElement> elements;

    public MoleculeGroup(int amount, List<FormulaElement> elements) {
        this.amount = amount;
        this.elements = elements;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<FormulaElement> getElements() {
        return elements;
    }

    public void setElements(List<FormulaElement> elements) {
        this.elements = elements;
    }
}
