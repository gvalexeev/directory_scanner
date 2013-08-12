package command.impl;

import command.core.ICommand;
import command.params.ScanParam;
import scanner.FolderScanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class Scan implements ICommand {
    private Map<ScanParam, String> paramsMap = new HashMap<>();

    @Override
    public void init(List<String> params) {
        for (ScanParam param : ScanParam.values()) {
            int index = params.indexOf("-" + param.param());

            if (index != -1 && params.size() >= (index + 1)) {
                paramsMap.put(param, params.get(index + 1));
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
        String threadName = "scan:" + paramsMap;
        FolderScanner folderScanner = new FolderScanner(paramsMap);
        folderScanner.setName(threadName);
        folderScanner.start();

        threadMap.put(threadName, folderScanner);
    }

    public Map<ScanParam, String> getParamsMap() {
        return paramsMap;
    }
}
