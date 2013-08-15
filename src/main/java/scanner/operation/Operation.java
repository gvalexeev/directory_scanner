package scanner.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.locks.ReentrantLock;

/**
 * $Id
 * <p>Title: Класс, обеспечивающий синхронизированное проведение основных операций с файлами</p>
 * <p>Description:
 * При проведении операции, поток вызывает соответствующий метод и производится попытка получить блокировку данного файла.
 * Если попытка неуспешна, поток продолжает выполнение основной задачи.
 * Наверное надо было сделать абстрактный класс... Но времени было мало :( Это вероятный дальнейший рефакторинг.
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 14.08.13</p>
 *
 * @version 1.0
 */
public class Operation {
    //Общий для всех методов объект блокировки
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Операция копирования файла
     *
     * @param source - файл - источник
     * @param target - файл - приемник
     * @throws IOException
     */
    public void performCopy(Path source, Path target) throws IOException {
        if (Files.exists(source)) {
            if (lock.tryLock()) {
                try {
                    if (Files.exists(source)) {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Операция перемещения файла
     *
     * @param source - файл - источник
     * @param target - файл - приемник
     * @throws IOException
     */
    public void performMove(Path source, Path target) throws IOException {
        if (Files.exists(source)) {
            //Попытка получения блокировки
            if (lock.tryLock()) {
                try {
                    if (Files.exists(source)) {
                        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
