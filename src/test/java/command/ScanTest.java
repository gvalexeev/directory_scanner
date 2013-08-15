package command;

import command.impl.Exit;
import command.impl.Scan;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import scanner.FolderScanner;

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
    private static final Logger log = Logger.getRootLogger();
    private Map<String, FolderScanner> threadMap = new HashMap<>();

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

            }
    };

    @Theory
    public void testCopy(Object... data) throws Exception {
        List<String> paramslist = Arrays.asList(((String) data[0]).split(" "));
        Scan scan = new Scan();
        scan.init(paramslist);
        assertTrue(scan.getParamsMap().toString(), scan.getParamsMap().size() == data[1]);

        List<String> errorsList = scan.validate();
        log.error(errorsList);
        assertTrue(errorsList.size() == data[2]);


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
//            for (FolderScanner scanner : threadMap.values()) {
//
//                scanner.interrupt();
//            }
        }
    }
}
