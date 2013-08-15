package validation;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * $Id
 * <p>Title: Класс - валидатор</p>
 * <p>Description: Содержит основные методы для проверки приходящих параметров</p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Checker {
    /**
     * Метод валидации файлового пути операционной системы
     * @param pathExp - строка, содержащая файловый путь
     * @return - возвращает строку с ошибкой или null, если все в порядке
     */
    public static String checkPath(String pathExp) {
        String returnVal = null;

        if (!isNull(pathExp)) {
            try {
                Path path = Paths.get(pathExp);

                if (!Files.exists(path)) {
                    returnVal = "File with path " + pathExp + " does not exists.";
                }
            } catch (InvalidPathException exp) {
                returnVal = "Invalid path string " + pathExp;
            }
        } else {
            returnVal = "Path string is empty.";
        }

        return returnVal;
    }

    /**
     * Метод проверки валидности регулярного выражения
     * @param regexpExp - строка с выражением
     * @return - возвращает строку с ошибкой или null, если все в порядке
     * TODO: валидация globe паттерна
     */
    public static String checkRegexp(String regexpExp) {
        String returnVal = null;

//        if (!isNull(regexpExp)) {
//            try {
//                Pattern.compile(regexpExp);
//            } catch (PatternSyntaxException exception) {
//                returnVal = "Regexp expression " + regexpExp + " is not valid. Error is " + exception.getDescription();
//            }
//        } else {
//            returnVal = "Regexp expression is empty.";
//        }
        return returnVal;
    }

    /**
     * Метод проверки валидности целочисленного значения
     * @param decimalExp - строка со значением.
     * @return - возвращает строку с ошибкой или null, если все в порядке
     */
    public static String checkDecimal(String decimalExp) {
        String returnVal = null;

        if (!isNull(decimalExp)) {
            try {
                long num = Long.valueOf(decimalExp);

                if (num < 0) {
                    returnVal = "Number must be positive " + decimalExp;
                }
            } catch (NumberFormatException exp) {
                returnVal = "Incorrect number value " + decimalExp;
            }
        } else {
            returnVal = "Integer value is empty.";
        }
        return returnVal;
    }

    /**
     * Метод проверки валидности булевого выражения
     * @param booleanExp - строка с булевым выражением
     * @return - возвращает строку с ошибкой или null, если все в порядке
     */
    public static String checkBoolean(String booleanExp) {
        String returnVal = null;

        if (!isNull(booleanExp)) {
            if (!"true".equalsIgnoreCase(booleanExp) & !"false".equalsIgnoreCase(booleanExp)) {
                returnVal = "Unknown boolean expression: " + booleanExp + "\nAvailable option values are true or false";
            }
        } else {
            returnVal = "Boolean value is empty.";
        }
        return returnVal;
    }

    /**
     *  Метод проверки строки на пустоту
     * @param value - строковое значение
     * @return - возвращает строку с ошибкой или null, если все в порядке
     */
    public static boolean isNull(String value) {
        return (value == null) || (value.trim().length() == 0);
    }
}
