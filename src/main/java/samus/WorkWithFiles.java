package samus;

import java.nio.file.Path;

public class WorkWithFiles
{
    /**
     * В данном методе необходимо пройти по каталогу и вывести в консоль следующие значение:
     *      - количество файлов в каталоге (включая во вложенных каталогах)
     * @param path - путь к каталогу, по которому необходимо предоставить информацию
     */
    public void walkingDirectory(Path path)
    {
        /*
        ...
         */
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
        /*
        ...
         */
    }

    /**
     * В данном методе необходимо реализовать создание копии файла средствами пакета {@link java.nio}
     * @param path - исходный файл
     * @return - true при успешном создании копии
     */
    public boolean copyFile(Path path)
    {
        /*
        ...
         */
        return false;
    }
}
