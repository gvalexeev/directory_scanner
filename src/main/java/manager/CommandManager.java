package manager;

import command.core.ICommand;
import command.impl.*;
import command.impl.Error;
import scanner.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class CommandManager {
    private boolean isExitCommand;
    private ICommand command;

    /**

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

        command.init(paramsList);
        List<String> errors = command.validate();

        if (errors.size() != 0) {
            command = new Error();
            command.init(errors);
        }

        return command;
    }

    public void execute(Map<String, FolderScanner> threadMap) {
        if (command != null) {
            command.execute(threadMap);
        }
    }

    public boolean isExitCommand() {
        return isExitCommand;
    }
}
