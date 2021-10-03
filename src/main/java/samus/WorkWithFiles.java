package samus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkWithFiles
{
    /**
     * В данном методе необходимо пройти по каталогу и вывести в консоль следующие значение:
     *      - количество файлов в каталоге (включая во вложенных каталогах)
     * @param path - путь к каталогу, по которому необходимо предоставить информацию
     */
    public void walkingDirectory(Path path)
    {
        if (path == null) {
            return;
        }

        try (Stream<Path> files = Files.list(path)) {
            System.out.println(files.count());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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
    public void warningsPrinting(Path sourceFile, Path destinationFile)
    {
        if (sourceFile == null || destinationFile == null) {
            return;
        }

        try {
            String forSourceFile  = Files.lines(sourceFile)
                    .filter(s -> s.startsWith("WARN"))
                    .map(s -> s + "\n")
                    .collect(Collectors.toList())
                    .toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(", ", "")
                    ;

            try (BufferedWriter writer = Files.newBufferedWriter(destinationFile, Charset.defaultCharset())) {
                writer.write(forSourceFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * В данном методе необходимо реализовать создание копии файла средствами пакета {@link java.nio}
     * @param path - исходный файл
     * @return - true при успешном создании копии
     */
    public boolean copyFile(Path path)
    {
        if (path == null) {
            return false;
        }

        if (path.toFile().exists()) {
            Path copy = path.getParent().resolve(Paths.get(path.getFileName() + "_copy"));

            try {
                Files.copy(path, copy);
                return true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return false;
    }
}
