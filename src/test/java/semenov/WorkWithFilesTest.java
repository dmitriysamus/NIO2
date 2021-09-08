package semenov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbp.semenov.WorkWithFiles;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class WorkWithFilesTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final WorkWithFiles workWithFiles = new WorkWithFiles();

    private final Path pathToWalk = Paths.get("src");
    private final Path srcPath = Paths.get("src/test/resources/logs.log");
    private Path dstPath = getCopyPath(srcPath);

    @BeforeEach
    private void catchOutput() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    private void resetOutput() {
        out.reset();
    }


    /**
     * Тест проверяет корректность выводимого количества файлов в директории
     */
    @Test
    public void walkingDirectoryTest() throws IOException {
        int expected = countFilesWithRecursion(pathToWalk.toFile());

        workWithFiles.walkingDirectory(pathToWalk);
        String output = out.toString().trim();
        int actual = Integer.valueOf(output);

        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тест проверяет копирование логов с уроавнем "WARN"
     */
    @Test
    public void warningsPrintingTest() throws IOException {
        String expected = "WARN No database could be detected\r\n" +
                "WARN Performing manual recovery";

        workWithFiles.warningsPrinting(srcPath, dstPath);
        Assertions.assertTrue(dstPath.toFile().exists());

        String actual = getContentFromFile(dstPath);
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тест проверяет создание копии файла
     */
    @Test
    public void copyFileTest() throws IOException {
        String expected = getContentFromFile(srcPath);

        if (dstPath.toFile().exists()) {
            Files.delete(dstPath);
        }

        workWithFiles.copyFile(srcPath);
        Assertions.assertTrue(dstPath.toFile().exists());

        String actual = getContentFromFile(dstPath);
        Assertions.assertEquals(expected, actual);
    }


    private int countFilesWithRecursion(File file) {
        if (!file.isDirectory()) {
            return 1;
        }

        int sum = 0;
        for (File tempFile : file.listFiles()) {
            sum += countFilesWithRecursion(tempFile);
        }

        return sum;
    }

    private String[] getOutputArray() {
        String output = out.toString();
        return output.split("[\r]\n");
    }

    private Path getCopyPath(Path path) {
        String dstName = "Copy" + path.toFile().getName();
        return path.resolveSibling(Paths.get(dstName));
    }

    private String getContentFromFile(Path path) throws IOException {
        return Files.readAllLines(path).stream()
                .collect(Collectors.joining("\r\n"));
    }
}
