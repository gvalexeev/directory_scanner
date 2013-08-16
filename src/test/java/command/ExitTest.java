package command;

import command.impl.Exit;
import command.impl.Scan;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scanner.FolderScanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
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
public class ExitTest {
    private static final Logger log = Logger.getRootLogger();
    private final Map<String, FolderScanner> threadMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        String line = "scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 60000 -includeSubfolders false -autoDelete true";

        List<String> list = Arrays.asList(line.split(" "));
        Scan scan = new Scan();
        scan.init(list);
        scan.validate();
        scan.execute(threadMap);
    }

    @Test
    public void testCopy() throws Exception {
        assertTrue(threadMap.size() == 1);

        FolderScanner scanner = (FolderScanner) threadMap.values().toArray()[0];
        assertTrue(scanner.isAlive());

        Exit exit = new Exit();
        exit.execute(threadMap);
        assertFalse(scanner.isAlive());
    }

    @After
    public void tearDown() throws Exception {


    }
}
