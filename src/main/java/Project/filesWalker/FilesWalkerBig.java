package Project.filesWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesWalkerBig extends FilesWalker {
    static List<String> rejectedFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.getParent().toString().contains("Техника")) {
            if (file.getFileName().toString().length() <= 50) {
                techMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString() + " БОЛЬШЕ 50 СИМВОЛОВ В Техника");
            }
        }
        if (file.getParent().toString().contains("Квалификация")) {
            if (file.getFileName().toString().length() <= 50) {
                qualMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString() + " БОЛЬШЕ 50 СИМВОЛОВ В Квалификация");
            }
        }
        if (file.getParent().toString().contains("Коммерческая")) {
            if (file.getFileName().toString().length() <= 50) {
                commMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            } else {
                rejectedFiles.add(file.getFileName().toString() + " БОЛЬШЕ 50 СИМВОЛОВ В Коммерческая");
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public void walk(String dirPath, FilesWalker filesWalker) throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, filesWalker);
        if (rejectedFiles.size() > 0) {
            rejectedFiles.forEach(System.out::println);
            throw new RuntimeException("БОЛЬШЕ 50 СИМВОЛОВ ДЛИНА ФАЙЛА!");
        }
        if (validation()) {
            throw new RuntimeException("VALIDATION AFTER WALK FAILED, CHECK DIRECTORIES!");
        }
    }

    public static void main(String[] args) throws IOException {
        FilesWalkerBig walker = new FilesWalkerBig();
        walker.startTest("/media/v/Samsung USB1/OTHER/TEST BIG", walker);
    }
}
