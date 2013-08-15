package manager;

import command.core.ICommand;
import command.impl.*;
import command.impl.Error;
import scanner.*;

import java.util.*;

/**
 * $Id
 * <p>Title: Класс-менеджер команд</p>
 * <p>Description:
 * С помощью метода resolve(String) распознает строку с командой и параметрами. Далее создается соответствующий объект и
 * производятся операции инициализации и валидации. Если валидация неуспешна, то возвращается объект Error(), который просто
 * выводит на экран список валидационных ошибок.
 * Если распознавание команды неуспешно(пользователь ввел абракадабру), выводится принт команды help с синтаксисом команд.
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class CommandManager {
    private boolean isExitCommand;
    private ICommand command;

    /**
     * Метод распознавания строковой команды.
     * @param input - строка с параметризированной командой
     * @return - объект - представдление соответствующей команды.
     */
    public ICommand resolve(String input) {
        List<String> paramsList = Arrays.asList(input.split(" "));
        String commandStr = paramsList.get(0);

        switch (commandStr) {
            case "scan":
                command = new Scan();
                break;
            case "exit":
                isExitCommand = true;
                command = new Exit();
                break;
            case "show":
                command = new Show();
                break;
            case "stop":
                command = new Stop();
                break;
            default:
                command = new Help();
        }

        //инициализация команды
        command.init(paramsList);
        //валидация
        List<String> errors = command.validate();

        //Если есть ошибки, выводим их пользователю
        if (errors.size() != 0) {
            command = new Error();
            command.init(errors);
        }

        return command;
    }

    /**
     * Выполнить команду
     * @param threadMap
     */
    public void execute(Map<String, FolderScanner> threadMap) {
        if (command != null) {
            command.execute(threadMap);
        }
    }

    /**
     * Метод, сигнализирующий о том, что была введена команда exit
     * @return
     */
    public boolean isExitCommand() {
        return isExitCommand;
    }
}
