package sbp.semenov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WorkWithFiles {
    /**
     * В данном методе необходимо пройти по каталогу и вывести в консоль следующие значение:
     * - количество файлов в каталоге (включая во вложенных каталогах)
     *
     * @param path - путь к каталогу, по которому необходимо предоставить информацию
     */
    public void walkingDirectory(Path path) throws IOException {
        long result;
        Predicate<Path> pathPredicate = p -> p.toFile().isFile();

        if (pathPredicate.test(path)) {
            result = 0;
        } else {
            result = Files.walk(path)
                    .filter(pathPredicate)
                    .count();
        }

        System.out.println(result);
    }

    /**
     * В данном методе необходимо все логи уровня WARN из исходного файла скопировать в новый файл.
     * Пример содержания исходного файла лога:
     * INFO Server starting
     * DEBUG Processes available = 10
     * WARN No database could be detected
     * DEBUG Processes available reset to 0
     * WARN Performing manual recovery
     * INFO Server successfully started
     *
     * @param sourceFile      - исходный файл с логом
     * @param destinationFile - целевой файл
     */
    public void warningsPrinting(Path sourceFile, Path destinationFile) throws IOException {
        Predicate<String> stringPredicate = s -> s.startsWith("WARN");

        String content = Files.readAllLines(sourceFile).stream()
                .filter(stringPredicate)
                .collect(Collectors.joining("\n"));

        Files.write(destinationFile, content.getBytes());
    }

    /**
     * В данном методе необходимо реализовать создание копии файла средствами пакета {@link java.nio}
     *
     * @param path - исходный файл
     * @return - true при успешном создании копии
     */
    public boolean copyFile(Path path) {
        Path dstPath = getCopyPath(path);
        try {
            Files.copy(path, dstPath);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private Path getCopyPath(Path path) {
        String dstName = "Copy" + path.toFile().getName();
        return path.resolveSibling(Paths.get(dstName));
    }

}
