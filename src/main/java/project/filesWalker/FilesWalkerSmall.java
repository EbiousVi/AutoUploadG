package project.filesWalker;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

public class FilesWalkerSmall extends FilesWalker {

    public FilesWalkerSmall(Map<Directories, Boolean> parts) {
        super(parts);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.getFileName().toString().startsWith("~$")) return FileVisitResult.CONTINUE;

        if (parts.get(Directories.TECH)) {
            if (file.getParent().toString().contains(Directories.TECH.name)) {
                techMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            }
        }
        if (parts.get(Directories.QUAL)) {
            if (file.getParent().toString().contains(Directories.QUAL.name)) {
                qualMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            }
        }
        if (parts.get(Directories.COMM)) {
            if (file.getParent().toString().contains(Directories.COMM.name)) {
                commMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public boolean doFilter() {
        return !isEmptyDirs();
    }
}
