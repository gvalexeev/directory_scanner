package scanner;

import command.params.ScanParam;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * $Id
 * <p>Title: Класс - поток, реализующий операцию сканирования</p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 10.08.13</p>
 *
 * @version 1.0
 */
public class FolderScanner extends Thread {
    private static final Logger log = Logger.getLogger(Thread.currentThread().getName());
    private Map<ScanParam, String> paramsMap;

    /**
     * Конструктор
     *
     * @param params - валидная мапа с параметрами, введенными в командную строку
     */
    public FolderScanner(Map<ScanParam, String> params) {
        this.paramsMap = params;
    }

    /*
    Volatile маркер, сигнализирующий потоку о безопасной остановке
     */
    private volatile boolean isRunning = true;

    /**
     * Метод безопасной остановки потока.
     */
    public void terminate() {
        isRunning = false;
    }

    @Override
    public void run() {
        //конфигурация личного логгера
        configureLogger();

        while (isRunning) {
            try {
                //получаем необходимые параметры
                Path startPath = Paths.get(paramsMap.get(ScanParam.INPUT_DIR));

                //Запускаем обход файлового дерева с передачей кастомной имплементации FileVisitor, где, собственно,
                //и происходит все действие
                Files.walkFileTree(startPath,
                        new CopyMatchingFileVisitor(
                                startPath,
                                paramsMap.get(ScanParam.OUTPUT_DIR),
                                paramsMap.get(ScanParam.MASK),
                                Boolean.parseBoolean(paramsMap.get(ScanParam.INCLUDE_SUB_FOLDERS)),
                                Boolean.parseBoolean(paramsMap.get(ScanParam.AUTO_DELETE))
                        )
                );

                //Не засыпать, если во время исполнения потоку сигнализировали об остановке
                if (isRunning) {
                    //Если выполнение продолжается, то поток засыпает на указанное в параметрах время
                    Thread.sleep(Long.valueOf(paramsMap.get(ScanParam.WAIT_INTERVAL)));
                }
            } catch (IOException e) {
                log.error(e);
                //При выбрасывании исключения, прекращаем цикл.
                isRunning = false;
            } catch (InterruptedException e) {
                log.error("Thread has been interrupted");
                isRunning = false;
            }
        }
    }

    /**
     * Getter для мапы с параметрами
     */
    public Map<ScanParam, String> getParamsMap() {
        return paramsMap;
    }

    /*
    Метод для ручной конфигурации логгера. Сконфигурирован таким образом, чтобы каждый поток писал в отдельный файл.
     */
    private void configureLogger() {
        org.apache.log4j.Logger log = Logger.getLogger(Thread.currentThread().getName());
        Properties props = new Properties();
        props.setProperty("log4j.appender.thread", "org.apache.log4j.RollingFileAppender");

        props.setProperty("log4j.appender.thread.maxFileSize", "100MB");
        props.setProperty("log4j.appender.thread.maxBackupIndex", "100");
        props.setProperty("log4j.appender.thread.File", "${user.dir}/logs/threads/" + Thread.currentThread().getName() + ".log");
        props.setProperty("log4j.appender.thread.threshold", "INFO");
        props.setProperty("log4j.appender.thread.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.thread.layout.ConversionPattern", "%d{ABSOLUTE} %5p [%t] %c - %m%n");
//        props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.logger." + Thread.currentThread().getName(), "INFO, thread");

        PropertyConfigurator.configure(props);
        log.info("thread started :" + Thread.currentThread().getName());
    }
}
