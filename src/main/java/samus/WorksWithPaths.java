package samus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorksWithPaths
{
    /**
     * Создать объект класса {@link Path}, проверить существование и чем является (файл или директория).
     * Если сущность существует, то вывести в консоль информацию:
     *      - абсолютный путь
     *      - родительский путь
     * Если сущность является файлом, то вывести в консоль:
     *      - размер
     *      - время последнего изменения
     * Необходимо использовать {@link Path}
     * @param fileName - имя файла
     * @return - true, если файл успешно создан
     */
    public boolean workWithPath(String fileName)
    {
        if (fileName != null) {
            Path path = Paths.get(fileName);
            System.out.println(path.toAbsolutePath());
            System.out.println(path.getParent());

            if (Files.exists(path)) {
                try {
                    System.out.println(Files.size(path));
                    System.out.println(Files.getLastModifiedTime(path));
                    return true;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return false;
    }
}
