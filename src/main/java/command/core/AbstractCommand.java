package command.core;

import scanner.FolderScanner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 11.08.13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class AbstractCommand implements ICommand{
    @Override
    public void init(List<String> params) {

    }

    @Override
    public List<String> validate() {
        return Collections.emptyList();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void execute(Map<String, FolderScanner> threadMap) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
