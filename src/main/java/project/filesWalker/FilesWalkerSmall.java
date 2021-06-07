package project.filesWalker;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesWalkerSmall extends FilesWalker {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.getParent().toString().contains("Техника")) {
            techMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
        }
        if (file.getParent().toString().contains("Квалификация")) {
            qualMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
        }
        if (file.getParent().toString().contains("Коммерческая")) {
            commMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public boolean startFiltration() {
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
}
