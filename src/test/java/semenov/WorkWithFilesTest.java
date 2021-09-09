package semenov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbp.semenov.WorkWithFiles;

import java.io.ByteArrayOutputStream;
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
    private final Path dstPath = getCopyPath(srcPath);

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
        int expected = countFilesWithRecursion(pathToWalk);

        workWithFiles.walkingDirectory(pathToWalk);
        String output = out.toString().trim();
        int actual = Integer.parseInt(output);

        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тест проверяет копирование логов с уроавнем "WARN"
     */
    @Test
    public void warningsPrintingTest() throws IOException {
        String expected = "No database could be detected\r\n" +
                "Performing manual recovery";

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
        Files.deleteIfExists(dstPath);

        workWithFiles.copyFile(srcPath);
        Assertions.assertTrue(dstPath.toFile().exists());

        String actual = getContentFromFile(dstPath);
        Assertions.assertEquals(expected, actual);
    }


    private int countFilesWithRecursion(Path path) {
        if (Files.isRegularFile(path)) {
            return 1;
        }

        try {
            return Files.list(path)
                    .map(p -> countFilesWithRecursion(p))
                    .reduce(0, Integer::sum);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Path getCopyPath(Path path) {
        String dstName = "Copy" + path.getFileName();
        return path.resolveSibling(Paths.get(dstName));
    }

    private String getContentFromFile(Path path) throws IOException {
        return Files.lines(path)
                .collect(Collectors.joining("\r\n"));
    }
}
