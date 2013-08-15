package command.params;

import validation.Checker;

/**
 * $Id
 * <p>Title: Параметры команды scan</p>
 * <p>Description:
 * Каждый параметр содержит свои описание и значение командной строки, а также умеет валидироваться :)
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public enum ScanParam {
    INPUT_DIR("inputDir", "-inputDir <path> : Input directory for scan operation\n") {
        @Override
        public String validate(String value) {
            return Checker.checkPath(value);
        }
    },
    OUTPUT_DIR("outputDir", "-outputDir <path> : Output directory to copy in\n") {
        @Override
        public String validate(String value) {
            return Checker.checkPath(value);
        }
    },
    MASK("mask", "-mask <regexp_expression> : File mask. Has to be a valid regexp expression\n") {
        @Override
        public String validate(String value) {
            return Checker.checkRegexp(value);
        }
    },
    WAIT_INTERVAL("waitInterval", "-waitInterval <positive_number> : Scanner cycle interval. Has to be a positive decimal number\n") {
        @Override
        public String validate(String value) {
            return Checker.checkDecimal(value);
        }
    },
    INCLUDE_SUB_FOLDERS("includeSubfolders", "-includeSubfolders <boolean_value> : This flag shows that scanner has to scan input folder recursively and keep directory structure while copying\n") {
        @Override
        public String validate(String value) {
            return Checker.checkBoolean(value);
        }
    },
    AUTO_DELETE("autoDelete", "-autoDelete <boolean_value> : Flag for autodele source files\n") {
        @Override
        public String validate(String value) {
            return Checker.checkBoolean(value);
        }
    };

    private final String param;
    private final String description;

    ScanParam(String name, String description) {
        this.description= description;
        this.param = name;
    }

    public String paramName() {
        return param;
    }

    public String description() {
        return description;
    }

    public String validate(String value) {
        return null;
    }

}
