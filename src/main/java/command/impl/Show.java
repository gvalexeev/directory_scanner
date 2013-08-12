package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class Show implements ICommand {
    private static final Logger log = Logger.getRootLogger();

    @Override
    public void init(List<String> params) {

    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
       for (String name : threadMap.keySet()) {
           log.info(name);
       }
    }
}
