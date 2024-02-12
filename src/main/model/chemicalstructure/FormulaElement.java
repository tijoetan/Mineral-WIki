package model.chemicalstructure;

import model.enums.AtomicSymbols;

public class FormulaElement {
    private AtomicSymbols symbol;
    private int count;

    public FormulaElement(AtomicSymbols symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AtomicSymbols getSymbol() {
        return symbol;
    }

    public void setSymbol(AtomicSymbols symbol) {
        this.symbol = symbol;
    }
}
