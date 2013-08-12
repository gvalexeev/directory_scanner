package scanner;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class CopyMatchingFileVisitor extends SimpleFileVisitor<Path> {
    private static final Logger log = Logger.getRootLogger();

    private final PathMatcher matcher;
    private Path outputDir;
    private Path inputDir;
    private boolean includeSubfolders;
    private boolean autoDelete;

    public CopyMatchingFileVisitor(Path inputDir, String outputDir, String mask, boolean includeSubfolders, boolean autoDelete) {
        this.inputDir = inputDir;
        this.outputDir = Paths.get(outputDir);
        this.includeSubfolders = includeSubfolders;
        this.autoDelete = autoDelete;

        matcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + mask);
    }

    // Compares the glob pattern against
    // the file or directory name.
    private void copy(Path source) throws IOException {
        Path name = source.getFileName();

        if (name != null && matcher.matches(name)) {
            if (autoDelete) {
                Files.move(source, outputDir.resolve(name), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } else {
                Files.copy(source, outputDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        copy(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //Нет копирования структуры папок
        return !includeSubfolders && !dir.equals(inputDir) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        //TODO: log to separate file
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }
}
