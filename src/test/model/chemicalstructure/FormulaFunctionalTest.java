package model.chemicalstructure;

import model.exceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormulaFunctionalTest {
    FormulaFunctional formula;
    @BeforeEach
    void beforeEach() {
        formula = new FormulaFunctional("MgAl2");
    }

    @Test
    void testSomething() {
        try {
            formula.parseFormula("(Mg, Pd)2(Mo, Ds)2Mg2Al(CO3)2");
        } catch (UnknownElementException e) {
            System.out.println("Failed");
        }
    }


}