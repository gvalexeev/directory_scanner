package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Команда error</p>
 * <p>Description: Является служебной командой, недоступной пользователю. Используется для вывода ошибок валидации</p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
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
