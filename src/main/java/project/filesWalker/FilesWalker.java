package project.filesWalker;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.TreeMap;

public abstract class FilesWalker extends SimpleFileVisitor<Path> {
    //KEY = PATH, VALUE = FILENAME;
    public final Map<String, String> techMap = new TreeMap<>();
    public final Map<String, String> qualMap = new TreeMap<>();
    public final Map<String, String> commMap = new TreeMap<>();
    public final Map<Directories, Boolean> parts;

    public FilesWalker(Map<Directories, Boolean> parts) {
        this.parts = parts;
    }

    public abstract boolean doFilter();

    public boolean isSuccessWalked(String dirPath) {
        if (!walk(dirPath)) {
            return false;
        }
        if (doFilter()) {
            printFoundedFiles();
            return true;
        } else {
            return false;
        }
    }

    boolean walk(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            Files.walkFileTree(path, this);
            return true;
        } catch (IOException e) {
            System.out.println("--> Ошибка при попытки обхода заданной папки! Указан не корректный путь <" + e.getMessage() + ">");
        } catch (InvalidPathException e) {
            System.out.println("--> Не допустимые символы в пути к папке! Недопустимые символы < " + e.getInput() + " >");
        }
        return false;
    }

    boolean isEmptyDirs() {
        boolean isEmptyDirs = techMap.isEmpty() && qualMap.isEmpty() && commMap.isEmpty();
        if (isEmptyDirs) {
            System.out.println("--> В папках Техника, Квалификация, Коммерческая отсутствуют файлы или структура папки не соответствует шаблону!");
        }
        return isEmptyDirs;
    }

    void printFoundedFiles() {
        System.out.println();
        if (parts.get(Directories.TECH)) {
            System.out.println("В Технике: " + techMap.size() + " файл");
            techMap.forEach((k, v) -> System.out.println("Техника: " + k));
            System.out.println("------------------------------------------------------");
        }
        if (parts.get(Directories.QUAL)) {
            System.out.println("В Квалификации: " + qualMap.size() + " файл");
            qualMap.forEach((k, v) -> System.out.println("Квалификация: " + k));
            System.out.println("------------------------------------------------------");
        }
        if (parts.get(Directories.COMM)) {
            System.out.println("В Коммерческой: " + commMap.size() + " файл");
            commMap.forEach((k, v) -> System.out.println("Коммерческая: " + k));
            System.out.println("------------------------------------------------------");
        }
        System.out.println("Всего: " + (techMap.size() + qualMap.size() + commMap.size()) + " файлов");
        System.out.println();
    }
}
