package model.chemicalstructure;

import enums.AtomicSymbols;

// Contains an Element and its amount

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
