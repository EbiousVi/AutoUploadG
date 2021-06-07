package project.filesWalker;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesWalkerBig extends FilesWalker {
    private final List<String> rejectedFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.getParent().toString().contains("Техника")) {
            if (file.getFileName().toString().length() <= 50) {
                techMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString());
            }
        }
        if (file.getParent().toString().contains("Квалификация")) {
            if (file.getFileName().toString().length() <= 50) {
                qualMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString());
            }
        }
        if (file.getParent().toString().contains("Коммерческая")) {
            if (file.getFileName().toString().length() <= 50) {
                commMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString());
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public boolean startFiltration() {
        if (!rejectedFiles.isEmpty()) {
            printOverSizeFileNameInfo();
            return false;
        }
        if (isEmptyDirs()) {
            System.out.println("Пустая папка! Попробуйте снова");
            return false;
        }
        if (!isCorrectFillingMaps()) {
            System.out.println("Число собранных файлов не соответствует числу находящихся в папке");
            return false;
        }
        return true;
    }

    private void printOverSizeFileNameInfo() {
        for (String str : rejectedFiles) {
            System.out.println(str.substring(0, 50) +
                    "... -> Имя заканчивается на 50-ом символе. Уменьшить длину, вкл расширение файла!");
            break;
        }
    }
}
