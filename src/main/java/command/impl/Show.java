package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Команда show</p>
 * <p>Description:
 * Выводит на экран список всех запущенных потоков с их параметрами
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Show implements ICommand {
    private static final Logger log = Logger.getRootLogger();

    @Override
    public void init(List<String> params) {
       //Можно сделать показ потоков по папкам.
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        StringBuilder builder = new StringBuilder("Working threads:\n");

        for (Map.Entry<String, FolderScanner> entry : threadMap.entrySet()) {
            builder.append("\tThread name: ").append(entry.getKey()).append("\n\t").
                    append(entry.getValue().getParamsMap()).append("\n\n");
        }

        System.out.print(builder);
    }
}
