package command;

import command.impl.Exit;
import command.impl.Scan;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import scanner.FolderScanner;
import test.core.TestResourceManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class ScanTest {
    private Map<String, FolderScanner> threadMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        TestResourceManager.createFileResources();
    }

    @DataPoints
    public static Object[][] correctParams = new Object[][]{
            {
                    "scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 60000 -includeSubfolders false -autoDelete true",
                    6,
                    0,
                    true
            },
    };

    @DataPoints
    public static Object[][] errorParams = new Object[][]{
            {
                    "scan -inputDir -outputDir d:/test/out -mask *test*.xml -waitInterval 60000 -includeSubfolders false -autoDelete true",
                    6,
                    1,
                    false

            },
            {
                    "scan -inputDir",
                    0,
                    1,
                    false

            }
    };

    @Theory
    public void test(Object... data) throws Exception {
        List<String> paramslist = Arrays.asList(((String) data[0]).split(" "));
        Scan scan = new Scan();

        scan.init(paramslist.subList(1, paramslist.size()));
        assertTrue(scan.getParamsMap().toString(), scan.getParamsMap().size() == data[1]);

        List<String> errorsList = scan.validate();
        assertTrue("Errors list size is " + errorsList.size() + "\nErrors:" + errorsList, errorsList.size() == data[2]);


        if ((Boolean) data[3]) {
            scan.execute(threadMap);
            assertTrue(threadMap.size() == 1);

        }
    }

    @After
    public void tearDown() throws Exception {
        if (threadMap.size() > 0) {
            Exit exit = new Exit();
            exit.execute(threadMap);
            threadMap.clear();
        }

        TestResourceManager.destroyResources();
    }
}
