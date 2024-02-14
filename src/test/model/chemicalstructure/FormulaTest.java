package model.chemicalstructure;

import exceptions.UnknownElementException;
import org.junit.jupiter.api.Test;

class FormulaTest {
    Formula f1;
    Formula f2;
    Formula f3;
    Formula f4;

    @Test
    void beforeEach() throws UnknownElementException {
        f1 = new Formula("MgAl2");
        f2 = new Formula("N2O2");
        f3 = new Formula("(Mg,Al, P, Po)C6H12O6(C, X, Zn)");
        System.out.println("Done");
    }





}