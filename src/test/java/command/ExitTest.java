package command;

import command.impl.Exit;
import command.impl.Scan;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import scanner.FolderScanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class ExitTest {
    private static final Logger log = Logger.getRootLogger();
    private final Map<String, FolderScanner> threadMap = new HashMap<>();

    @Test
    public void testCopy() throws Exception {
        String line = "scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 60000 -includeSubfolders false -autoDelete true";

        List<String> list = Arrays.asList(line.split(" "));
        Scan scan = new Scan();
        scan.init(list);
        scan.validate();
        scan.execute(threadMap);

        assertTrue(threadMap.size() == 1);

        FolderScanner scanner = (FolderScanner) threadMap.values().toArray()[0];
        assertTrue(scanner.isAlive());

        Exit exit = new Exit();
        exit.execute(threadMap);
        assertFalse(scanner.isAlive());
    }
}
