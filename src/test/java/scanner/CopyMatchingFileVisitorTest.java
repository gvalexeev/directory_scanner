package scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.core.TestResourceManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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
    private Path in;
    private Path out;

    @Before
    public void setUp() throws Exception {
        Map<String, Path> map = TestResourceManager.createFileResources();
        in = map.get("in");
        out = map.get("out");
    }

    @DataPoints
    public static Object[][] fileTreeData = new Object[][]{
            {"*.xml", 3, false, false},
            {"*.html", 1, false, false},
            {"*test*.xml", 2, false, false},
            {"1*1.*", 1, false, false},
            {"1.*", 1, false, false},
            {"*.xml", 3, false, true, 0, 3},
            {"*.xml", 5, true, true, 2, 1}
    };

    @Theory
    public void testCopy(Object... data) throws Exception {
        Files.walkFileTree(in, new CopyMatchingFileVisitor(
                in,
                out,
                (String) data[0],
                (Boolean) data[2],
                (Boolean) data[3]));

        //check amount of files
        assertTrue(errorData(data),FileUtils.listFiles(out.toFile(), null, true).size() == data[1]);

        //subfolders
        if ((Boolean) data[2]) {
            //check amount of subfolders. Take out 1 cause commons.io inserts root dit too. fast workaround
            assertTrue(errorData(data), FileUtils.listFilesAndDirs(out.toFile(), FalseFileFilter.FALSE, DirectoryFileFilter.DIRECTORY).size() - 1 == data[4]);
        }

        //autodelete
        if ((Boolean) data[3]) {
            Collection<File> files = FileUtils.listFiles(in.toFile(), null, true);

            //check amount of files in source dir
            assertTrue(errorData(data), files.size() == data[5]);
        }
    }

    @After
    public void tearDown() throws Exception {
        TestResourceManager.destroyResources();
    }


    private String errorData(Object... data) {
        StringBuilder builder = new StringBuilder();
        builder.append("\nTest data:" + Arrays.asList(data)).
        append("\nInput directory content: " + FileUtils.listFilesAndDirs(in.toFile(), TrueFileFilter.TRUE, TrueFileFilter.TRUE)).
        append("\nOutput directory content: " + FileUtils.listFilesAndDirs(out.toFile(), TrueFileFilter.TRUE, TrueFileFilter.TRUE));
        return builder.toString();
    }
}
