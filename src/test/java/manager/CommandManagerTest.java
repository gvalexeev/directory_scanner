package manager;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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

    @DataPoints
    public static Object[][] correctParams = new Object[][]{
            {"scan -inputDir d:/test/in -outputDir d:/test/out -mask *test*.xml -waitInterval 5000 -includeSubfolders false -autoDelete true",
                    6,
                    0
            }
    };

    @Theory
    public void testResolve() throws Exception {


    }
}
