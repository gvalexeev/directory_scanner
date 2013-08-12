package scanner;

import command.params.ScanParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */

public class FolderScanner extends Thread {
    private Map<ScanParam, String> paramsMap;

    public FolderScanner(Map<ScanParam, String> params) {
        this.paramsMap = params;
    }

    public volatile boolean isRunning = true;

    public void terminate() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
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

                if (isRunning) {
                    Thread.sleep(Long.valueOf(paramsMap.get(ScanParam.WAIT_INTERVAL)));
                }
            } catch (IOException e) {
                //Todo
                e.printStackTrace();
            } catch (InterruptedException e) {
                isRunning = false;
            }
        }
    }
}
