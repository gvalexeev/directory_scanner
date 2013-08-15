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
    private List<String> threadNameList = new ArrayList<>();

    @Override
    public void init(List<String> threadIds) {
        for (int i = 1; i < threadIds.size(); i++) {
            threadNameList.add(threadIds.get(i));
        }
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
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
                        log.warn("Thread has been interrupted!");
                    }
                }

                threadMap.remove(threadName);
                System.out.println("Thread " + threadName + " successfully stopped");
            } else {
                illegalThreadNamesList.add(threadName + "\n");
            }

        }

        if (illegalThreadNamesList.size() > 0) {
            System.out.println("Cannot find threads with such names:\n" + illegalThreadNamesList);
            Show showCommand = new Show();
            showCommand.execute(threadMap);
        }
    }
}
