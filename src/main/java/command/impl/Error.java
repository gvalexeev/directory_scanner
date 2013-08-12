package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class Error implements ICommand {
    private static final Logger log = Logger.getRootLogger();
    private List<String> errorsList = new ArrayList<>();

    @Override
    public void init(List<String> errors) {
        errorsList.addAll(errors);
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        for (String error : errorsList) {
            System.out.println(error);
        }
    }
}
