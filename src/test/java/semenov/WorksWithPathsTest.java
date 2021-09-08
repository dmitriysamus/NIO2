package semenov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbp.semenov.WorksWithPaths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorksWithPathsTest{
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final WorksWithPaths worksWithPaths = new WorksWithPaths();
    private final Path srcPath = Paths.get("src/test/resources/logs.log");


    /**
     * Проверяет печать информации при наличие файла
     */
    @Test
    public void workWithPathTest() throws IOException {
        System.setOut(new PrintStream(out));

        worksWithPaths.printPathPaths(srcPath);
        worksWithPaths.printPathInfo(srcPath);

        String expected = out.toString();
        out.reset();

        System.setOut(new PrintStream(out));

        boolean returnBool = worksWithPaths.workWithPath(srcPath.toString());
        String actual = out.toString();

        out.reset();

        Assertions.assertEquals(expected, actual);
        Assertions.assertTrue(returnBool);
    }
}
