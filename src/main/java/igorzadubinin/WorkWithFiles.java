package igorzadubinin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class WorkWithFiles
{
    /**
     * В данном методе необходимо пройти по каталогу и вывести в консоль следующие значение:
     *      - количество файлов в каталоге (включая во вложенных каталогах)
     * @param path - путь к каталогу, по которому необходимо предоставить информацию
     */
    public void walkingDirectory(Path path) throws IOException
    {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path doesn't exist");
        }
        if (Files.isRegularFile(path))
            throw new IllegalArgumentException("Not a directory");
        Long result = Files.walk(path)
                .filter(Files::isRegularFile)
                .count();
        System.out.println(result);
    }

    /**
     * В данном методе необходимо все логи уровня WARN из исходного файла скопировать в новый файл.
     * Пример содержания исходного файла лога:
     *  INFO Server starting
     *  DEBUG Processes available = 10
     *  WARN No database could be detected
     *  DEBUG Processes available reset to 0
     *  WARN Performing manual recovery
     *  INFO Server successfully started
     * @param sourceFile - исходный файл с логом
     * @param destinationFile - целевой файл
     */
    public void warningsPrinting(Path sourceFile, Path destinationFile) throws IOException
    {
        if (!Files.exists(sourceFile)) {
            throw new IllegalArgumentException("Source file doesn't exist");
        }
        if (Files.isDirectory(sourceFile))
            throw new IllegalArgumentException("Not a file");
        if (Files.exists(destinationFile))
            Files.delete(destinationFile);
        Files.createFile(destinationFile);
        Files.lines(sourceFile)
                .filter(s -> s.startsWith("WARN"))
                .map(s -> s.substring(5))
                .forEach(s -> {
                    try {
                        Files.write(destinationFile, (s + "\r\n").getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * В данном методе необходимо реализовать создание копии файла средствами пакета {@link java.nio}
     * @param path - исходный файл
     * @return - true при успешном создании копии
     */
    public boolean copyFile(Path path) throws IOException
    {
        if (!Files.exists(path) || Files.isDirectory(path))
            return false;
        String file = path.getFileName().toString();
        String fileName;
        String fileExt = "";
        if (file.contains(".")) {
            fileName = file.substring(0, file.lastIndexOf("."));
            fileExt = file.substring(file.lastIndexOf("."));
        }
        else
            fileName = file;
        Optional<Integer> ver = Files.list(path.getParent())
                .map(p -> p.getFileName().toString())
                .map(s -> s.substring(0, s.lastIndexOf(".") == -1 ? s.length() : s.lastIndexOf(".")))
                .filter(s -> s.matches(fileName + "_copy\\d+"))
                .map(s -> Integer.parseInt(s.substring(s.lastIndexOf("_copy") + 5)))
                .max(Integer::compareTo);
        int fileVer = ver.orElse(0) + 1;
        Path newPath = path.toAbsolutePath().getParent().resolve(Paths.get(fileName + "_copy" + fileVer + fileExt));
        Files.copy(path, newPath);
        return true;
    }
}
