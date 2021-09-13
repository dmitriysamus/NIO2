package igorzadubinintest;

import igorzadubinin.WorkWithFiles;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class WorkWithFilesTest {
    WorkWithFiles wwf = new WorkWithFiles();
    final String TEST_DIR = "src\\test\\java\\igorzadubinintest\\TestData\\WorkWithFiles\\";
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    void walkingDirectoryTest() throws IOException {
        Path path = Paths.get(TEST_DIR + "walkingDirectoryTest");
        setUp();
        wwf.walkingDirectory(path);
        assertEquals("5", outputStreamCaptor.toString().trim());
        tearDown();
    }

    @Test
    void warningsPrintingTest() throws IOException {
        Path sourceFile = Paths.get(TEST_DIR + "warningsPrintingTest\\log.txt");
        Path destinationFile = Paths.get(TEST_DIR + "warningsPrintingTest\\log_warn.txt");
        wwf.warningsPrinting(sourceFile, destinationFile);
        assertTrue(FileUtils.contentEquals(destinationFile.toFile(), new File(TEST_DIR + "warningsPrintingTest\\log_sample.txt")));

    }

    @Test
    void copyFileTest() throws IOException {
        Path path = Paths.get(TEST_DIR + "copyFileTest\\file.txt");
        assertTrue(wwf.copyFile(path));
        String newFile = TEST_DIR + "copyFileTest\\file_copy1.txt";
        assertTrue(FileUtils.contentEquals(path.toFile(), new File(newFile)));
    }

    private void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    private void tearDown() {
        System.setOut(standardOut);
    }
}