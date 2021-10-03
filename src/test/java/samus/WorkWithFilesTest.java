package samus;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class WorkWithFilesTest {
    private WorkWithFiles workWithFiles;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    /**
     * Подготовка данных для тестов.
     */
    @BeforeEach
    void init() {
        workWithFiles = new WorkWithFiles();
        outputStream = new ByteArrayOutputStream();
        printStream = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Очистка объектов после тестов.
     */
    @AfterEach
    void clean() {
        workWithFiles = null;
        outputStream = null;
        System.setOut(printStream);
    }

    /**
     * Проверяет метод walkingDirectory().
     */
    @Test
    void walkingDirectory_test() {
        workWithFiles.walkingDirectory(Paths.get("src\\test\\java\\samus\\test"));
        Assert.assertEquals("2", outputStream.toString().trim());
    }

    /**
     * Проверяет метод warningsPrintingSuccess().
     */
    @Test
    void warningsPrintingSuccess_test() {
        String sourceFile = "src\\test\\java\\samus\\test\\test1";
        String destinationFile = "src\\test\\java\\samus\\test\\test2";
        try {
            workWithFiles.warningsPrinting(Paths.get(sourceFile), Paths.get(destinationFile));
            Assert.assertEquals("WARN No database could be detected\n" + "WARN Performing manual recovery\n",
                    Files.lines(Paths.get(destinationFile))
                            .map(s -> s + "\n")
                            .collect(Collectors.toList())
                            .toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(", ", "")
                            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверяет метод copyFile().
     */
    @Test
    void copyFile_test() {
        Assert.assertEquals(true, workWithFiles.copyFile(Paths.get("src\\test\\java\\samus\\test\\test1")));
        try {
            Assert.assertEquals("WARN No database could be detected\n" + "WARN Performing manual recovery\n",
                    Files.lines(Paths.get("src\\test\\java\\samus\\test\\test1_copy"))
                            .filter(s -> s.startsWith("WARN"))
                            .map(s -> s + "\n")
                            .collect(Collectors.toList())
                            .toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(", ", ""));
        } catch (IOException e) {
            e.printStackTrace();
        };
        File file = new File("src\\test\\java\\samus\\test\\test1_copy");
        file.delete();
    }
}
