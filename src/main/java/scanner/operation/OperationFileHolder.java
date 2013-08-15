package scanner.operation;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * $Id
 * <p>Title: Синглтон для глобальной регистрации файлов и производимых операций</p>
 * <p>Description: Данный класс необходим для мультипоточного доступа к файлам.
 * Принцип таков:
 * Любой поток, при нахождении файла, регистрирует его в данном классе. При регистрации с файлом ассоциируется объект
 * Операции @link{Operation.class}. Если данный файл уже был зарегистрирован, то возвращается ассоциированный с ним
 * объект операции.
 * При удалении файла из системы, он также перестает существовать в регистраторе.
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 14.08.13</p>
 *
 * @version 1.0
 */
public class OperationFileHolder {
    private static OperationFileHolder ourInstance;
    //Мапа с файлами и операциями для синхронизированного доступа
    private ConcurrentMap<Path, Operation> fileOperationsMap = new ConcurrentHashMap<>();

    /**
     * Получение синглтон инстанса
     */
    public static OperationFileHolder getInstance() {
        if (ourInstance == null) {
            synchronized (OperationFileHolder.class) {
                if (ourInstance == null) {
                    ourInstance = new OperationFileHolder();
                }
            }
        }
        return ourInstance;
    }

    private OperationFileHolder() {
    }

    /**
     * Метод регистрации файла. Операция производится атомарно.
     * @param key - объект Path
     * @return если файл уже был зарегистрирован, то возвращается ассоциированное старое значение. Если же нет, то новое.
     */
    public Operation register(Path key) {
        Operation oldVal = fileOperationsMap.putIfAbsent(key, new Operation());
        return oldVal == null ? fileOperationsMap.get(key) : oldVal;
    }

    /**
     * Атомарная операция удаления из регистратора.
     * @param path - объект Path, указывающий на определенный путь в операционной системе.
     */
    public void unregister(Path path) {
        fileOperationsMap.remove(path);
    }
}
