package model.entries;

import model.chemicalstructure.Formula;
import model.enums.Cleavage;
import model.enums.CrystalStructure;
import model.modelexceptions.UnknownElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MineralTest {

    Mineral m1;
    Mineral m2;
    Mineral m3;

    @BeforeEach
    void beforeEach() {
        m1 = new Mineral("Quartz");
        m2 = new Mineral("Galena");
        m3 = new Mineral("Acanthite");
    }

    @Test
    void testConstruction() {
        assertEquals(Cleavage.NA, m1.getCleavage());
        assertEquals(CrystalStructure.NA, m1.getCrystalStructure());
        assertEquals(0.0f, m1.getHardness());
        assertEquals(0.0f, m1.getDensity());
        assertEquals(0.0f, m1.getIndexOfRefraction());
    }

    @Test
    void setIndexOfRefractionValid() {
        m1.setIndexOfRefraction(1.0f);
        assertEquals(1.0f, m1.getIndexOfRefraction());
    }

    @Test
    void setIndexOfRefractionInvalid() {
        m1.setIndexOfRefraction(-1.0f);
        assertEquals(0.0f, m1.getIndexOfRefraction());
    }

    @Test
    void setHardnessValid() {
        m2.setHardness(2.3f);
        assertEquals(2.3f, m2.getHardness());
    }

    @Test
    void setHardnessInvalid() {
        m2.setHardness(-0.4f);
        assertEquals(0.0f, m2.getHardness());
    }

    @Test
    void setDensityValid() {
        m3.setDensity(13.8f);
        assertEquals(13.8f, m3.getDensity());
    }

    @Test
    void setDensityInvalid() {
        m3.setDensity(-19.0f);
        assertEquals(0.0f, m3.getDensity());
    }

    @Test
    void setCrystalStructure() {
        m1.setCrystalStructure(CrystalStructure.CUBIC);
        m2.setCrystalStructure(CrystalStructure.HEXAGONAL);
        m3.setCrystalStructure(CrystalStructure.ORTHORHOMBIC);

        assertEquals(CrystalStructure.CUBIC, m1.getCrystalStructure());
        assertEquals(CrystalStructure.HEXAGONAL, m2.getCrystalStructure());
        assertEquals(CrystalStructure.ORTHORHOMBIC, m3.getCrystalStructure());

        m1.setCrystalStructure(CrystalStructure.NA);
        m2.setCrystalStructure(CrystalStructure.NA);
        m3.setCrystalStructure(CrystalStructure.NA);

        assertEquals(CrystalStructure.CUBIC, m1.getCrystalStructure());
        assertEquals(CrystalStructure.HEXAGONAL, m2.getCrystalStructure());
        assertEquals(CrystalStructure.ORTHORHOMBIC, m3.getCrystalStructure());
    }

    @Test
    void setCleavage() {
        m1.setCleavage(Cleavage.CUBIC);
        m2.setCleavage(Cleavage.BASAL);
        m3.setCleavage(Cleavage.PRISMATIC);

        assertEquals(Cleavage.CUBIC, m1.getCleavage());
        assertEquals(Cleavage.BASAL, m2.getCleavage());
        assertEquals(Cleavage.PRISMATIC, m3.getCleavage());

        m1.setCleavage(Cleavage.NA);
        m3.setCleavage(Cleavage.NA);
        m3.setCleavage(Cleavage.NA);

        assertEquals(Cleavage.CUBIC, m1.getCleavage());
        assertEquals(Cleavage.BASAL, m2.getCleavage());
        assertEquals(Cleavage.PRISMATIC, m3.getCleavage());
    }

    @Test
    void giveAllAttributes() {
        Formula m1Formula = new Formula();
        Cleavage m1Cleavage = Cleavage.CUBIC;
        CrystalStructure m1Structure = CrystalStructure.HEXAGONAL;
        float m1Hardness = 2.5f;
        float m1IOR = 1.8f;
        float m1Density = 19.6f;
        try {
            m1Formula = new Formula("Pr");
        } catch (UnknownElementException e) {
            fail();
        }

        m1.setCleavage(m1Cleavage);
        m1.setHardness(m1Hardness);
        m1.setCrystalStructure(m1Structure);
        m1.setIndexOfRefraction(m1IOR);
        m1.setDensity(m1Density);
        m1.setGeneralFormula(m1Formula);

        String entry = m1.giveAllAttributes();

        String desiredEntry =  String.format("Name: %s | Crystal Structure: %s | Formula: %s "
                        + "| Hardness: %s | Density: %s | IOR: %s | Cleavage %s",
                "Quartz",
                m1Structure,
                m1Formula.getFormulaAsString(),
                m1Hardness,
                m1Density,
                m1IOR,
                m1Cleavage);

        assertEquals(desiredEntry, entry);
    }


}