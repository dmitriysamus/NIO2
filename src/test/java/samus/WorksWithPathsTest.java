package samus;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorksWithPathsTest {

    WorksWithPaths worksWithPaths;

    /**
     * Подготовка данных для тестов.
     */
    @BeforeEach
    void init() {
        worksWithPaths = new WorksWithPaths();
    }

    /**
     * Очистка объектов после тестов.
     */
    @AfterEach
    void clean() {
        worksWithPaths = null;
    }

    /**
     * Проверяет метод workWithPath(), передается существующий файл.
     */
    @Test
    void workWithPath_existsTest() {
        Assert.assertEquals(true, worksWithPaths.workWithPath("src\\test\\java\\samus\\test\\test1"));
    }

    /**
     * Проверяет метод workWithPath(), передается несуществующий файл.
     */
    @Test
    void workWithPath_notExistsTest() {
        Assert.assertEquals(false, worksWithPaths.workWithPath("drqwrasdrfasdf"));
    }
    
}
