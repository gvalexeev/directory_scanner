package command.core;

import scanner.FolderScanner;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {
    public void init(List<String> params);
    public List<String> validate();
    public void execute(Map<String, FolderScanner> threadMap);
}
