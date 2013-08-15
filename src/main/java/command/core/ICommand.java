package command.core;

import scanner.FolderScanner;

import java.util.List;
import java.util.Map;

/**
 * $Id
 * <p>Title: Общий интерфейс для команд.</p>
 * <p>Description:
 * Содержит три основнх метода:
 * init(List<String>) - инициализация сырой введенной команды с параметрами. Приводит в божеский вид, в соответствии с
 * принадлежащим ему Enum. Если параметров нет, то данный метод возможно оставить пустым.
 * validate() - производит валидацию параметров. На выходе получается список с ошибками.
 * execute(Map<String, FolderScanner>) - реализация активных действий команды(печать, запуск/остановка потока и т.д.)
 * В мапе передаются потоки и команда может делать с ними все что угодно(останавливать, удалять, запускать...)
 * </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public interface ICommand {
    public void init(List<String> params);
    public List<String> validate();
    public void execute(Map<String, FolderScanner> threadMap);
}
