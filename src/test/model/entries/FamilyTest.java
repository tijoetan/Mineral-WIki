package model.entries;

import model.chemicalstructure.Formula;
import model.modelexceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTest {

    Family f1;
    Family f2;
    Mineral m1;
    Mineral m2;
    Mineral m3;
    Mineral m4;

    @BeforeEach
    void beforeEach() {
        f1 = new Family("f1");
        f2 = new Family("f2");

        m1 = new Mineral("m1");
        m2 = new Mineral("m2");
        m3 = new Mineral("m3");
        m4 = new Mineral("m4");

    }

    @Test
    void testConstructor() {
        assertTrue(f1.getMineralsWithFamily().isEmpty());
        assertTrue(f2.getMineralsWithFamily().isEmpty());
    }

    @Test
    void addMineralsWithFamily() {
        f1.addMineralsWithFamily(new ArrayList<>(Arrays.asList(m1, m2)));
        f2.addMineralsWithFamily(new ArrayList<>(Arrays.asList(m3, m4, m4)));

        assertEquals(2, f1.getMineralsWithFamily().size());
        assertEquals(m1, f1.getMineralsWithFamily().get(0));
        assertEquals(m2, f1.getMineralsWithFamily().get(1));

        assertEquals(2, f2.getMineralsWithFamily().size());
        assertEquals(m3, f2.getMineralsWithFamily().get(0));
        assertEquals(m4, f2.getMineralsWithFamily().get(1));

    }

    @Test
    void printAllAttributes() {
        Formula f1Formula = new Formula();
        Formula f2Formula = new Formula();

        try {
            f1Formula = new Formula("SiO2");
            f2Formula = new Formula("Cu(CO3)(OH)");
        } catch (UnknownElementException e) {
            fail();
        }


        f1.addMineralsWithFamily(new ArrayList<>(Arrays.asList(m1, m2)));
        f2.addMineralsWithFamily(new ArrayList<>(Arrays.asList(m3, m4)));
        f1.setGeneralFormula(f1Formula);
        f2.setGeneralFormula(f2Formula);

        String f1Result = f1.giveAllAttributes();
        String f2Result = f2.giveAllAttributes();

        String f1Expected = String.format("Name: %s | General Formula: %s | Subs: %s",
                "f1",
                f1Formula.getFormulaAsString(),
                "m1 m2 ");

        String f2Expected = String.format("Name: %s | General Formula: %s | Subs: %s",
                "f2",
                f2Formula.getFormulaAsString(),
                "m3 m4 ");

        assertEquals(f1Expected, f1Result);
        assertEquals(f2Expected, f2Result);


    }
}