package command.impl;

import command.core.ICommand;
import org.apache.log4j.Logger;
import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Команда exit</p>
 * <p>Description:
 * Безопасная остановка работающих потоков и выход из программы.
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Exit implements ICommand {
    private static final Logger log = Logger.getRootLogger();

    @Override
    public void init(List<String> params) {
        //doNothing
    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        //Пробегаемся по мапе
        for (FolderScanner folderScannerThread : threadMap.values()) {
            //Если поток спит, то допустимо остановить его грубо(interrupt()).
            if (folderScannerThread.getState() == Thread.State.TIMED_WAITING) {
                folderScannerThread.interrupt();
            } else {
                //Если нет, то проставляем ему флаг остановки и джоинимся к нему.
                folderScannerThread.terminate();

                try {
                    folderScannerThread.join();
                } catch (InterruptedException e) {
                    log.warn("Thread has been interrupted!");
                }
            }
        }
    }
}
