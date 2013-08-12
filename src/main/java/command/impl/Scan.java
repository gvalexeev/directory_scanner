package command.impl;

import command.core.ICommand;
import command.params.ScanParam;
import scanner.FolderScanner;
import validation.Checker;

import java.util.*;

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

        for (ScanParam param : ScanParam.values()) {
            String result = param.validate(paramsMap.get(param));

            if (result != null && result.trim().length() != 0) {
                errors.add(result);
            }
        }

        return errors;
    }


    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        String threadName = "scan:" + paramsMap;
        FolderScanner folderScanner = new FolderScanner(this);
        folderScanner.setName(threadName);
        folderScanner.start();

        threadMap.put(threadName, folderScanner);
    }

    public Map<ScanParam, String> getParamsMap() {
        return paramsMap;
    }
}
