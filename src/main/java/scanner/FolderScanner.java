package scanner;

import command.impl.Scan;
import command.params.ScanParam;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */

public class FolderScanner extends Thread {
    private Scan scanCommand;

    public FolderScanner(Scan scanCommand) {
        this.scanCommand = scanCommand;
    }

    public volatile boolean isRunning = true;

    public void terminate() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                final Map<ScanParam, String> paramsMap = scanCommand.getParamsMap();

                Path startPath = Paths.get(paramsMap.get(ScanParam.INPUT_DIR));

                Files.walkFileTree(startPath,
                        new CopyMatchingFileVisitor(
                                startPath,
                                paramsMap.get(ScanParam.OUTPUT_DIR),
                                paramsMap.get(ScanParam.MASK),
                                Boolean.parseBoolean(paramsMap.get(ScanParam.INCLUDE_SUB_FOLDERS)),
                                Boolean.parseBoolean(paramsMap.get(ScanParam.AUTO_DELETE))
                        )
                );

                Thread.sleep(Long.valueOf(paramsMap.get(ScanParam.WAIT_INTERVAL)));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    }
}
