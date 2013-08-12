package main;

import command.core.ICommand;
import command.impl.Exit;
import org.apache.log4j.Logger;
import manager.CommandManager;
import scanner.FolderScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Gera
 * Date: 10.08.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final Logger log = Logger.getRootLogger();

    public static void main(String[] args) {
        Map<String, FolderScanner> threadMap = new HashMap<>();

        Scanner scanIn = new Scanner(System.in);

        while (true) {
            //Со стрелочкой выглядит привлекательнее :)
            System.out.print(">");
            String input = scanIn.nextLine();

            CommandManager manager = new CommandManager();
            manager.resolve(input);
            manager.execute(threadMap);

            if (manager.isExitCommand()) {
                try {
                    System.out.println("Waiting for other threads...");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.warn("Interrupted");
                }

                break;
            }
        }

        scanIn.close();
    }
}
