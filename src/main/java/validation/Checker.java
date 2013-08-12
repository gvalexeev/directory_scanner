package validation;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class Checker {
    public static String checkPath(String pathExp) {
        String returnVal = null;
        try {
            Path path = Paths.get(pathExp);

            if (!Files.exists(path)) {
                returnVal = "File with path " + pathExp + " does not exists";
            }
        } catch (InvalidPathException exp) {
            returnVal = "Invalid path string " + pathExp;
        }

        return returnVal;
    }

    public static String checkWildcard(String wildcardExp) {
        return null;
    }

    public static String checkDecimal(String decimalExp) {
        String returnVal = null;

        try {
            long num = Long.valueOf(decimalExp);

            if (num < 0) {
               returnVal = "Number must be positive " + decimalExp;
            }
        } catch (NumberFormatException exp) {
            returnVal = "Incorrect number value " + decimalExp;
        }
        return returnVal;
    }

    public static String checkBoolean(String booleanExp) {
        return null;
    }
}
