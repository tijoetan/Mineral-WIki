package persistence;

import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

class TableReaderTest {

    TableReader testReader;
    FamilyTable familyTable;
    MineralTable mineralTable;

    @BeforeEach
    void runBefore() {
        familyTable = new FamilyTable();
        mineralTable = new MineralTable();
        testReader = new TableReader("data/something2.json", familyTable, mineralTable);
    }

    @Test
    void readFile() {
    }

    @Test
    void setupTables() {
        try {
            testReader.setupTables();
            System.out.println("he");
        } catch (IOException e) {
           fail();
        }
    }

    @Test
    void setUpMineralTable() {
    }

    @Test
    void setUpFamily() {
    }
}