package persistence;

import model.entries.Family;
import model.entries.Mineral;
import model.modelexceptions.DuplicationException;
import model.tableentry.FamilyTable;
import model.tableentry.MineralTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TableWriterTest {

    TableWriter testWriter;

    @BeforeEach
    void runBefore() {
        testWriter = new TableWriter("data/tests/actualWrittenFile.json");
    }

    @Test
    void openValidFile() {
        try {
            testWriter.open();
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    @Test
    void openInvalidFileExpectFileNotFoundException() {
        try {
            testWriter = new TableWriter("");
            testWriter.open();
            fail();
        } catch (FileNotFoundException e) {
            // Expected
        }
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

        testWrittenFilesAreEqual();

    }

    private void testWrittenFilesAreEqual() {
        TableReader expected = new TableReader("data/tests/expectedWrittenFile.json",
                new FamilyTable(),
                new MineralTable());
        TableReader actual = new TableReader("data/tests/actualWrittenFile.json",
                new FamilyTable(),
                new MineralTable());
        try {
            assertEquals(expected.readFile().toString(), actual.readFile().toString());
        } catch (IOException | InvalidFileException e) {
            throw new RuntimeException(e);
        }
    }
}