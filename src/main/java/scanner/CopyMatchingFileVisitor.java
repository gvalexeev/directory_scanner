package scanner;

import org.apache.log4j.Logger;
import scanner.operation.Operation;
import scanner.operation.OperationFileHolder;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * $Id
 * <p>Title: Реализация обхода дерева файлов с проведением операций над объектами, удовлетворяющими условиям.</p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class CopyMatchingFileVisitor extends SimpleFileVisitor<Path> {
    private static final Logger log = Logger.getLogger(Thread.currentThread().getName());

    private final PathMatcher matcher;
    private Path outputDir;
    private Path inputDir;
    private boolean includeSubfolders;
    private boolean autoDelete;

    /**
     * Конструктор.
     * @param inputDir - Path входной директории.
     * @param outputDir - строка с результирующей директорией
     * @param mask - маска файлов
     * @param includeSubfolders - флаг, сигнализирующий о включении поддиректорий в путь обхода
     * @param autoDelete - удалять найденный файлы?
     */
    public CopyMatchingFileVisitor(Path inputDir, Path outputDir, String mask, boolean includeSubfolders, boolean autoDelete) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.includeSubfolders = includeSubfolders;
        this.autoDelete = autoDelete;

        //Создаем матчер с glob выражением.
        matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + mask);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path name = file.getFileName();

        //Проведение проверки имени файла на удовлетворение условий маски
        if (name != null && matcher.matches(name)) {
            //Создание синглтон холдера с операциями
            OperationFileHolder holder = OperationFileHolder.getInstance();
            //Регистрация найденного файла в холдере. Возвращает объект операции над файлом для синхронизации потоков.
            Operation oper = holder.register(file);

            //Определение реального пути файла, относительно выходной директории. Например, если имя файла 1.txt, то
            //резулитурующий объект Path будет создан с путем {outputDir}/1.txt
            Path realPath = outputDir.resolve(name);

            if (includeSubfolders) {
                //Если стоит маркер включения подпапок, то производим операцию вытягивания пути файла относительно
                //содержащей его директории. Например, если файл нахордится в {inputDir}/sub_folder/sub_sub_folder/<file_name>,
                //то результатом будет /sub_folder/sub_sub_folder/<file_name>
                Path subPath = file.subpath(inputDir.getNameCount(), file.getNameCount());

                if (!name.equals(subPath)) {
                    //Если файл находится не в корне входной директории, добавляем полученный относительный путь к пути
                    //выходной директории и создаем недостающие поддиректории.
                    realPath = outputDir.resolve(subPath);
                    Files.createDirectories(realPath.getParent());
                }
            }

            if (autoDelete) {
                //Если стоит флаг удаления исходного файла после копирования, производим операцию перемещения и
                //удаляем информацию о регистрации данного файла из рег.палаты :)
                oper.performMove(file, realPath);
                holder.unregister(file);
            } else {
                //Если флаг = false, то просто производим копирование данного файла. Регистрация сохраняется.
                oper.performCopy(file, realPath);
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return !includeSubfolders && !dir.equals(inputDir) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        log.error(exc);
        return FileVisitResult.CONTINUE;
    }
}
