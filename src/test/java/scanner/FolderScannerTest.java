package scanner;

import command.params.ScanParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.core.TestResourceManager;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * $Id
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 16.08.13</p>
 *
 * @version 1.0
 */
public class FolderScannerTest {
    private Map<ScanParam, String> map = new HashMap<>();
    private FolderScanner scanner;


    @Before
    public void setUp() throws Exception {
        TestResourceManager.createFileResources();
        map.put(ScanParam.INPUT_DIR, "d:/test/in");
        map.put(ScanParam.OUTPUT_DIR, "d:/test/out");
        map.put(ScanParam.AUTO_DELETE, "false");
        map.put(ScanParam.INCLUDE_SUB_FOLDERS, "true");
        map.put(ScanParam.MASK, "*.txt");
        map.put(ScanParam.WAIT_INTERVAL, "5000");

        scanner = new FolderScanner(map);
        scanner.start();
    }

    @Test
    public void testThread() {
        assertNotNull("Thread instance is null", scanner);
        assertTrue("Thread is not alive", scanner.isAlive());
    }

    @After
    public void tearDown() throws Exception {
        if (scanner != null && scanner.isAlive()) {
            scanner.terminate();
            scanner.join();
        }

        TestResourceManager.destroyResources();
    }
}
