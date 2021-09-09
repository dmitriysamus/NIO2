package sbp.semenov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class WorksWithPaths {
    public static void main(String[] args) throws IOException {
        new WorksWithPaths().workWithPath("src/test/resources/logs.log");
    }

    /**
     * Создать объект класса {@link Path}, проверить существование и чем является (файл или директория).
     * Если сущность существует, то вывести в консоль информацию:
     * - абсолютный путь
     * - родительский путь
     * Если сущность является файлом, то вывести в консоль:
     * - размер
     * - время последнего изменения
     * Необходимо использовать {@link Path}
     *
     * @param fileName - имя файла
     * @return - true, если файл успешно создан
     */
    public boolean workWithPath(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        if (!Files.exists(path)) {
            return false;
        }

        printPathPaths(path);

        if (Files.isRegularFile(path)) {
            printPathInfo(path);
        }

        return true;
    }

    public void printPathPaths(Path path) {
        Path absolutePath = path.toAbsolutePath();
        System.out.printf("Absolute path: %s\n", absolutePath);

        String parentPath = absolutePath.getParent() + "";
        System.out.printf("Parent path: %s\n", parentPath);
    }

    public void printPathInfo(Path path) throws IOException {
        long fileSize = Files.size(path);
        System.out.printf("Files size: %d\n", fileSize);

        FileTime lastModified = Files.getLastModifiedTime(path);
        System.out.printf("Date of last modification: %s\n", lastModified);
    }
}
