package Project.filesWalker;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.TreeMap;

public class FilesWalker extends SimpleFileVisitor<Path> {
    //KEY = PATH, VALUE = FILENAME;
    public static Map<String, String> techMap = new TreeMap<>();
    public static Map<String, String> qualMap = new TreeMap<>();
    public static Map<String, String> commMap = new TreeMap<>();

    public static long techPreVisitDirSize;
    public static long qualPreVisitDirSize;
    public static long commPreVisitDirSize;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (dir.getFileName().toString().contains("Техника")) {
            techPreVisitDirSize = Files.walk(dir).filter(x -> !Files.isDirectory(x)).count();
        }
        if (dir.getFileName().toString().contains("Квалификация")) {
            qualPreVisitDirSize = Files.walk(dir).filter(x -> !Files.isDirectory(x)).count();
        }
        if (dir.getFileName().toString().contains("Коммерческая")) {
            commPreVisitDirSize = Files.walk(dir).filter(x -> !Files.isDirectory(x)).count();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    public void walk(String dirPath, FilesWalker filesWalker) throws IOException {
        Path path = Paths.get(dirPath);
        Files.walkFileTree(path, filesWalker);
        if (validation()) {
            printValidationInfo();
            throw new RuntimeException("CHECK DIRECTORIES");
        }
    }

    public boolean validation() {
        return techMap.size() != FilesWalker.techPreVisitDirSize ||
                qualMap.size() != FilesWalker.qualPreVisitDirSize ||
                commMap.size() != FilesWalker.commPreVisitDirSize;
    }

    public void startTest(String path, FilesWalker walker) throws IOException {
        walker.walk(path, walker);
        FilesWalkerBig.techMap.forEach((k, v) -> System.out.println("techMap: " + k));
        System.out.println("------------------------------------------------------");
        FilesWalkerBig.qualMap.forEach((k, v) -> System.out.println("qualMap: " + k));
        System.out.println("------------------------------------------------------");
        FilesWalkerBig.commMap.forEach((k, v) -> System.out.println("commMap: " + k));
        printValidationInfo();
    }

    public void printValidationInfo() {
        System.out.println("VISIT TECH SIZE = " + FilesWalkerBig.techMap.size() + " = " + FilesWalker.techPreVisitDirSize + " PREVISIT TECH SIZE");
        System.out.println("VISIT QUAL SIZE = " + FilesWalkerBig.qualMap.size() + " = " + FilesWalker.qualPreVisitDirSize + " PREVISIT QUAL SIZE");
        System.out.println("VISIT COMM SIZE = " + FilesWalkerBig.commMap.size() + " = " + FilesWalker.commPreVisitDirSize + " PREVISIT COMM SIZE");
    }
}
