package command.params;

import validation.Checker;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
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
    MASK("mask", "-mask <wild_card_expression> : File mask. Has to be a valid wildcard expression\n") {
        @Override
        public String validate(String value) {
            return Checker.checkWildcard(value);
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

    public String param() {
        return param;
    }

    public String description() {
        return description;
    }

    public String validate(String value) {
        return null;
    }

}
