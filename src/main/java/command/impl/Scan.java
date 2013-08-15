package command.impl;

import command.core.ICommand;
import command.params.ScanParam;
import scanner.FolderScanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Команда scan</p>
 * <p>Description:
 * Запускает поток сканирования. А в остальном все как у всех :)
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Scan implements ICommand {
    private Map<ScanParam, String> paramsMap = new HashMap<>();

    @Override
    public void init(List<String> params) {
        for (ScanParam param : ScanParam.values()) {
            int index = params.indexOf("-" + param.paramName());

            if (index != -1 && params.size() >= (index + 1)) {
                String paramValue = params.get(index + 1);
                paramsMap.put(param, paramValue);
            }
        }
    }

    @Override
    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (paramsMap.size() > 0) {
            for (ScanParam param : ScanParam.values()) {
                String result = param.validate(paramsMap.get(param));

                if (result != null && result.trim().length() != 0) {
                    errors.add(result);
                }
            }
        } else {
            errors.add("No params specified.");
        }

        return errors;
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        FolderScanner folderScanner = new FolderScanner(paramsMap);
        String threadName = "scan_" + folderScanner;

        folderScanner.setName(threadName);
        folderScanner.start();

        threadMap.put(threadName, folderScanner);
    }

    public Map<ScanParam, String> getParamsMap() {
        return paramsMap;
    }
}
