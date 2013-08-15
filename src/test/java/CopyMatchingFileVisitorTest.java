import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import scanner.CopyMatchingFileVisitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * $Id
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
@RunWith(Theories.class)
public class CopyMatchingFileVisitorTest {
    private static final Logger log = Logger.getRootLogger();

    private String[] files = {
            "1.xml",
            "test.xml",
            "test.123.xml",
            "1test1.html",
            "test/test222test.xml",
            "test/test/111.xml"
    };
    private Path in;
    private Path out;

    @Before
    public void setUp() throws Exception {
        in = Paths.get("d:\\Tests\\input");
        Files.createDirectories(in);

        out = Paths.get("d:\\Tests\\output");
        Files.createDirectories(out);

        //TODO: bad, Winnie, very bad. Need dynamic resolve of subFolders.
        Path subFolder = in.resolve("test/test");
        Files.createDirectories(subFolder);

        for (String fileName : files) {
            Path testFile = in.resolve(fileName);
            Files.createFile(testFile);
        }
    }

    @DataPoints
    public static Object[][] fileTreeData = new Object[][]{
            {"d:\\Tests\\input", "d:\\Tests\\output", "*.xml", 5, false, false},
            {"d:\\Tests\\input", "d:\\Tests\\output", "*.html", 1, false, false},
            {"d:\\Tests\\input", "d:\\Tests\\output", "*test*.xml", 3, false, false},
            {"d:\\Tests\\input", "d:\\Tests\\output", "1*1.*", 2, false, false},
            {"d:\\Tests\\input", "d:\\Tests\\output", "1.*", 1, false, false},
            {"d:\\Tests\\input", "d:\\Tests\\output", "*.xml", 5, false, true, 0, 1},
            {"d:\\Tests\\input", "d:\\Tests\\output", "*.xml", 5, false, true, 2, 1}
    };

    @Theory
    public void testCopy(Object... data) throws Exception {
        Path path = Paths.get((String) data[0]);

        Path rootPath = Files.walkFileTree(path, new CopyMatchingFileVisitor(
                path,
                (String) data[1],
                (String) data[2],
                (Boolean) data[4],
                (Boolean) data[5]));

        log.info("\nTest data:" + Arrays.asList(data) +
                "\nResult is: " + Paths.get((String) data[1]).toFile().list().length +
                "\nDirectory content: " + Arrays.asList(Paths.get((String) data[1]).toFile().list()));
        assertTrue(FileUtils.listFiles(Paths.get((String) data[1]).toFile(), null, true).size() == data[3]);

        //subfolders
        if ((Boolean) data[4]) {
            log.info("\nTest data:" + Arrays.asList(data) +
                    "\nResult is: " + rootPath.toFile().list().length +
                    "\nDirectory content: " + FileUtils.listFiles(in.toFile(), null, true));

            assertTrue(FileUtils.listFilesAndDirs(rootPath.toFile(), FalseFileFilter.FALSE, DirectoryFileFilter.DIRECTORY).size() == data[6]);
        }

        //autodelete
        if ((Boolean) data[5]) {
            log.info("\nTest data:" + Arrays.asList(data) +
                    "\nResult is: " + rootPath.toFile().list().length +
                    "\nDirectory content: " + FileUtils.listFiles(in.toFile(), null, true));

            assertTrue(FileUtils.listFiles(in.toFile(), null, true).size() == data[7]);
        }
    }

    @After
    public void tearDown() throws Exception {
        Path root = in.getParent();

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                throw exc;
            }
        });
    }
}
