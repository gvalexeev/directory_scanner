package command;

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
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Theories.class)
public class ScanTest {
    private static final Logger log = Logger.getRootLogger();

    @DataPoints
    public static Object[][] correctParams = new Object[][]{
            {"scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 60000 -includeSubfolders false -autoDelete true",
                    6,
                    0
            }
    };

    @Theory
    public void testCopy(Object... data) throws Exception {
        List<String> list = Arrays.asList(((String) data[0]).split(" "));
        Scan scan = new Scan();
        scan.init(list);

        assertTrue(scan.getParamsMap().toString(), scan.getParamsMap().size() == data[1]);

        assertTrue(scan.validate().size() == data[2]);

        Map<String, FolderScanner> threadMap = new HashMap<>();
        scan.execute(threadMap);

        assertTrue(threadMap.size() == 1);
    }
}
