package igorzadubinin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    public boolean workWithPath(String fileName) throws IOException
    {
        Path path = Paths.get(fileName);
        if (Files.exists(path)) {
            System.out.println("Абсолютный путь: " + path.toAbsolutePath());
            System.out.println("Родительский путь: " + path.toAbsolutePath().getParent());
            if (Files.isRegularFile(path)) {
                System.out.println("Размер (в байтах): " + Files.size(path));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
                String lastModified = dtf.format(Files.getLastModifiedTime(path).toInstant().atZone(ZoneId.systemDefault()));
                System.out.println("Время последнего изменения: " + lastModified);
            }
        }
        else {
            Files.createFile(path);
            return true;
        }
        return false;
    }
}
