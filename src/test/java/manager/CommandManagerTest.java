package manager;

import command.core.ICommand;
import command.impl.Error;
import command.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.core.TestResourceManager;

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
public class CommandManagerTest {

    @Before
    public void setUp() throws Exception {
       TestResourceManager.createFileResources();
    }

    @DataPoints
    public static Object[][] commands = new Object[][]{
            {
                    "scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 5000 -includeSubfolders false -autoDelete true",
                    new Scan()
            },
            {
                    "exit",
                    new Exit()
            },
            {
                    "stop",
                    new Error()
            },
            {
                    "help",
                    new Help()
            },
            {
                    "show",
                    new Show()
            },
            {
                    "scan -inputDir -outputDir d:/test/out -mask *test*.xml -waitInterval 5000 -includeSubfolders false -autoDelete true",
                    new Error()
            },
            {
                    "whevfcwbjhcvghwvbbcbw",
                    new Help()
            },
            {
                    "stop jhwvcwjh",
                    new Stop()
            }

    };

    @Theory
    public void testResolve(Object... data) throws Exception {
        CommandManager manager = new CommandManager();
        ICommand command = manager.resolve((String) data[0]);

        assertTrue(command.getClass().isInstance(data[1]));

    }

    @After
    public void tearDown() throws Exception {
        TestResourceManager.destroyResources();
    }
}
