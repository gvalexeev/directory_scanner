package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.*;

/**
 * $Id
 * <p>Title: Команда stop</p>
 * <p>Description:
 * Останавливает указанный поток.
 * Допустимо указать несколько потоков через пробел. Если пользователь вдруг ошибся, то выведется список
 * потоков, которые не удалось остановить, а далее список работающих для корректировки.
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Stop implements ICommand {
    private static final Logger log = Logger.getRootLogger();
    private List<String> threadNameList;

    @Override
    public void init(List<String> threadIds) {
        this.threadNameList = threadIds;
    }

    @Override
    public List<String> validate() {
        return threadNameList.size() == 0 ?
                Arrays.asList("No thread name has been entered! Please specify valid thread name or see help for syntax.") :
                Collections.<String>emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        List<String> illegalThreadNamesList = new ArrayList<>();

        for (String threadName : threadNameList) {
            FolderScanner scanner = threadMap.get(threadName);

            if (scanner != null) {
                if (scanner.getState() == Thread.State.TIMED_WAITING) {
                    scanner.interrupt();
                } else {
                    scanner.terminate();

                    try {
                        scanner.join();
                    } catch (InterruptedException e) {
                        log.warn("Thread has been interrupted! " + scanner.getName());
                    }
                }

                threadMap.remove(threadName);
                System.out.println("Thread " + threadName + " successfully stopped");
            } else {
                illegalThreadNamesList.add(threadName);
            }

        }

        if (illegalThreadNamesList.size() > 0) {
            System.out.println("Cannot find threads with such names:\n" + illegalThreadNamesList);
            Show showCommand = new Show();
            showCommand.execute(threadMap);
        }
    }
}
