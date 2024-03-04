package persistence;

import model.entries.Family;
import model.entries.Mineral;
import model.modelexceptions.DuplicationException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TableWriterTest {

    TableWriter testWriter;

    @BeforeEach
    void runBefore() {
        testWriter = new TableWriter("data/something2.json");
    }

    @Test
    void open() {
    }

    @Test
    void close() {
    }

    @Test
    void writeToDestination() {
        FamilyTable ftable = new FamilyTable();
        MineralTable mtable = new MineralTable();
        try {
            ftable.addEntry(new Family("f1"));
            ftable.addEntry(new Family("f2"));
            mtable.addEntry(new Mineral("m1"));
            mtable.addEntry(new Mineral("m2"));
        } catch (DuplicationException e) {
            fail();
        }

        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
           fail();
        }
        testWriter.writeToDestination(mtable, ftable);
        testWriter.close();


    }
}