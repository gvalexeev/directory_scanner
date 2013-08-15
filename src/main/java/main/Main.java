package main;

import manager.CommandManager;
import scanner.FolderScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * $Id
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        //Мапа - хранилище выполняющихся объектов потоков с ключом по их имени. Времени было мало, может в джаве есть
        //встроенные средства.
        Map<String, FolderScanner> threadMap = new HashMap<>();

        //Сканер конадной строки
        Scanner scanIn = new Scanner(System.in);

        while (true) {
            //Со стрелочкой выглядит куда привлекательнее :)
            System.out.print(">");
            String input = scanIn.nextLine();

            //создается менеджер
            CommandManager manager = new CommandManager();
            //резолвится пользовательская бели... команда
            manager.resolve(input);
            //... и выполняется
            manager.execute(threadMap);

            //если был введен exit, идем пить чай
            if (manager.isExitCommand()) {
                break;
            }
        }

        scanIn.close();
    }
}
