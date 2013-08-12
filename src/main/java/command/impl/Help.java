package command.impl;

import command.core.ICommand;
import command.params.HelpParam;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class Help implements ICommand {
    private static final Logger log = Logger.getRootLogger();
    private HelpParam commandParam;

    @Override
    public void init(List<String> params) {
        if (params.size() == 2) {
            String commandName = params.get(1);

            for (HelpParam param : HelpParam.values()) {
                if (param.getCommand().equalsIgnoreCase(commandName)) {
                    commandParam = param;
                }
            }
        }
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        if (commandParam != null) {
            System.out.println(commandParam.getInfo());
        } else {
            for (HelpParam helpParam : HelpParam.values()) {
                System.out.println(helpParam.getInfo());
            }
        }
    }
}
