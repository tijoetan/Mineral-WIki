package model.chemicalstructure;

import model.enums.AtomicSymbols;

// Data representation containing an Element and its amount
public class FormulaElement {
    private final AtomicSymbols symbol;
    private final int count;

    // EFFECTS: initializes the FormulaElement
    public FormulaElement(AtomicSymbols symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }

    //getters
    public int getCount() {
        return count;
    }

    public AtomicSymbols getSymbol() {
        return symbol;
    }

}
