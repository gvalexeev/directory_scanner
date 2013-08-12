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
        for (FolderScanner folderScannerThread : threadMap.values()) {
            if (folderScannerThread.getState() == Thread.State.TIMED_WAITING) {
                folderScannerThread.interrupt();
            } else {
                folderScannerThread.terminate();

                try {
                    folderScannerThread.join();
                } catch (InterruptedException e) {
                    log.warn("Thread has been interrupted!");
                }
//                while(folderScannerThread.) {
//                }
            }

//            try {
//                folderScannerThread.join();
//            } catch (InterruptedException e) {
//                log.warn("Thread has been interrupted!");
//            }
        }
    }
}
