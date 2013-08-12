package manager;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
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
