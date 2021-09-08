package sbp.semenov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorksWithPaths {
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

        if (!path.toFile().exists()) {
            Files.createFile(path);
        }

        if (!path.toFile().exists()) {
            return false;
        }

        printPathPaths(path);

        if (path.toFile().isFile()) {
            printPathInfo(path);
        }

        return true;
    }

    public void printPathPaths(Path path) {
        File file = path.toFile();

        String absolutePath = file.getAbsolutePath();
        System.out.printf("Absolute path: %s\n", absolutePath);

        String parentPath = file.getParent();
        System.out.printf("Parent path: %s\n", parentPath);
    }

    public void printPathInfo(Path path) {
        File file = path.toFile();

        long fileSize = file.length();
        System.out.printf("Files size: %d\n", fileSize);

        long lastModified = file.lastModified();
        String formattedTime = getFormattedTime(lastModified);
        System.out.printf("Date of last modification: %s\n", formattedTime);
    }

    private String getFormattedTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
