package project.filesWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;

public abstract class FilesWalker extends SimpleFileVisitor<Path> {
    //KEY = PATH, VALUE = FILENAME;
    public Map<String, String> techMap = new TreeMap<>();
    public Map<String, String> qualMap = new TreeMap<>();
    public Map<String, String> commMap = new TreeMap<>();

    private long techPreVisitDirSize;
    private long qualPreVisitDirSize;
    private long commPreVisitDirSize;

    public abstract boolean startFiltration();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (dir.getFileName().toString().contains("Техника")) {
            techPreVisitDirSize = Files.walk(dir).filter(file -> !Files.isDirectory(file)).count();
        }
        if (dir.getFileName().toString().contains("Квалификация")) {
            qualPreVisitDirSize = Files.walk(dir).filter(file -> !Files.isDirectory(file)).count();
        }
        if (dir.getFileName().toString().contains("Коммерческая")) {
            commPreVisitDirSize = Files.walk(dir).filter(file -> !Files.isDirectory(file)).count();
        }
        return FileVisitResult.CONTINUE;
    }

    public boolean isSuccessWalked(String dirPath) {
        walk(dirPath);
        if (startFiltration()) {
            printAllFoundFiles();
            return true;
        } else {
            return false;
        }
    }

    void walk(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            Files.walkFileTree(path, this);
        } catch (IOException e) {
            System.out.println("Ошибка при попытки обхода заданной папки! " + e.getMessage());
        } catch (InvalidPathException e) {
            System.out.println("Указан не корректный путь! " + e.getMessage());
        }
    }

    boolean isCorrectFillingMaps() {
        return techMap.size() == techPreVisitDirSize &&
                qualMap.size() == qualPreVisitDirSize &&
                commMap.size() == commPreVisitDirSize;
    }

    boolean isEmptyDirs() {
        return techMap.isEmpty() && qualMap.isEmpty() && commMap.isEmpty();
    }

    void printAllFoundFiles() {
        System.out.println("В технике: " + techMap.size() + " файл");
        techMap.forEach((k, v) -> System.out.println("Техника: " + k));
        System.out.println("------------------------------------------------------");
        System.out.println("В квалификации: " + qualMap.size() + " файл");
        qualMap.forEach((k, v) -> System.out.println("Квалификация: " + k));
        System.out.println("------------------------------------------------------");
        System.out.println("В коммерческой: " + commMap.size() + " файл");
        commMap.forEach((k, v) -> System.out.println("Коммерческая: " + k));
        System.out.println("Всего: " + (techMap.size() + qualMap.size() + commMap.size()) + " файлов");
    }
}
