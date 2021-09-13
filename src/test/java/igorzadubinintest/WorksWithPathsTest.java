package igorzadubinintest;

import igorzadubinin.WorksWithPaths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class WorksWithPathsTest {
    WorksWithPaths wwp = new WorksWithPaths();
    final String TEST_DIR = "src\\test\\java\\igorzadubinintest\\TestData\\WorksWithPaths\\";
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    private void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * Сущность существует, является файлом.
     * Проверяется:
     * 1. Метод возвращает false
     * 2. Вывод совпадает со значением переменной expected
     */
    @Test
    void workWithPathTest1() throws IOException {
        String fileName = TEST_DIR + "workWithPathTest\\test.txt";
        assertFalse(wwp.workWithPath(fileName));
        String expected = String.format("Абсолютный путь: C:\\Users\\igorz\\IdeaProjects\\Homework\\Lesson11\\src\\test\\java\\igorzadubinintest\\TestData\\WorksWithPaths\\workWithPathTest\\test.txt%n" +
                "Родительский путь: C:\\Users\\igorz\\IdeaProjects\\Homework\\Lesson11\\src\\test\\java\\igorzadubinintest\\TestData\\WorksWithPaths\\workWithPathTest%n" +
                "Размер (в байтах): 12%n" +
                "Время последнего изменения: 13:53:55 12.09.2021");
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    /**
     * Сущность существует, является директорией.
     * Проверяется:
     * 1. Метод возвращает false
     * 2. Вывод совпадает со значением переменной expected
     */
    @Test
    void workWithPathTest2() throws IOException {
        String fileName = TEST_DIR + "workWithPathTest\\TestDir";
        assertFalse(wwp.workWithPath(fileName));
        String expected = String.format("Абсолютный путь: C:\\Users\\igorz\\IdeaProjects\\Homework\\Lesson11\\src\\test\\java\\igorzadubinintest\\TestData\\WorksWithPaths\\workWithPathTest\\TestDir%n" +
                "Родительский путь: C:\\Users\\igorz\\IdeaProjects\\Homework\\Lesson11\\src\\test\\java\\igorzadubinintest\\TestData\\WorksWithPaths\\workWithPathTest");
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    /**
     * Сущность не существует.
     * Проверяется:
     * 1. Метод возвращает true
     * 2. Файл создан
     */
    @Test
    void workWithPathTest3() throws IOException {
        String fileName = TEST_DIR + "workWithPathTest\\newFile.txt";
        assertTrue(wwp.workWithPath(fileName));
        assertTrue(Files.exists(Paths.get(fileName)));
    }

    @AfterEach
    private void tearDown() {
        System.setOut(standardOut);
    }
}