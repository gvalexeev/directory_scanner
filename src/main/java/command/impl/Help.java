package command.impl;

import command.core.ICommand;
import command.params.HelpParam;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Команда help</p>
 * <p>Description:
 * Выводит много полезной информации по использованию данного функционала.
 * Если ввести help<command_name> то выведется ман по указанной команде
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
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
