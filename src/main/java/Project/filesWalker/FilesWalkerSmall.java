package Project.filesWalker;

import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
        FilesWalkerSmall walker = new FilesWalkerSmall();
        walker.startTest("/media/v/Samsung USB1/OTHER/TEST", walker);
    }
}
