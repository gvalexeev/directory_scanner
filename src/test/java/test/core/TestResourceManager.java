/*
* $Id
*
* (C) Copyright 1997 i-Teco, CJSK. All Rights reserved.
* i-Teco PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*
* Эксклюзивные права 1997 i-Teco, ЗАО.
* Данные исходные коды не могут использоваться и быть изменены
* без официального разрешения компании i-Teco.          
*/
package test.core;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * $Id
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 16.08.13</p>
 *
 * @version 1.0
 */
public class TestResourceManager {
    private static final String[] files = {
            "1.xml",
            "test.xml",
            "test.123.xml",
            "1test1.html",
            "test/test222test.xml",
            "test/test/111.xml"
    };

    private static final String notEmptyInputPath = "d:\\test\\in";
    private static final String notEmptyOutputPath = "d:\\test\\out";
//    private static final String emptyInputPath = "d:\\test\\empty\\in";
//    private static final String emptyOutputPath = "d:\\test\\empty\\out";


    public static Map<String, Path> createFileResources() throws IOException {
        Path in = Paths.get(notEmptyInputPath);
        Files.createDirectories(in);

        Path out = Paths.get(notEmptyOutputPath);
        Files.createDirectories(out);

        for (String fileName : files) {
            Path testFile = in.resolve(fileName);
            Files.createDirectories(testFile.getParent());
            Files.createFile(testFile);
        }

        Map<String, Path> map = new HashMap<>();
        map.put("in", in);
        map.put("out", out);

        return map;
    }

    public static void destroyResources() throws IOException {
        Path root = Paths.get(notEmptyInputPath).getParent();

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                throw exc;
            }
        });
    }

}
