package model.chemicalstructure;

import model.enums.AtomicSymbols;

import java.util.ArrayList;
import java.util.Arrays;

public class CommonCovalentIons {
    public final ArrayList<FormulaElement> carbonate = new ArrayList<>(Arrays.asList(
            new FormulaElement(AtomicSymbols.C, 1), new FormulaElement(AtomicSymbols.O, 3)));

}
