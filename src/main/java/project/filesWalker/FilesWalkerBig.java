package project.filesWalker;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FilesWalkerBig extends FilesWalker {
    private List<String> rejectedFiles = new ArrayList<>();

    public FilesWalkerBig(Map<Directories, Boolean> parts) {
        super(parts);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (file.getFileName().toString().startsWith("~$")) return FileVisitResult.CONTINUE;

        if (parts.get(Directories.TECH)) {
            if (file.getParent().toString().contains(Directories.TECH.name)) {
                if (file.getFileName().toString().length() <= 50) {
                    techMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
                } else {
                    rejectedFiles.add(file.getFileName().toString());
                }
            }
        }

        if (parts.get(Directories.QUAL)) {
            if (file.getParent().toString().contains(Directories.QUAL.name)) {
                if (file.getFileName().toString().length() <= 50) {
                    qualMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
                } else {
                    rejectedFiles.add(file.getFileName().toString());
                }
            }
        }

        if (parts.get(Directories.COMM)) {
            if (file.getParent().toString().contains(Directories.COMM.name)) {
                if (file.getFileName().toString().length() <= 50) {
                    commMap.put(file.toAbsolutePath().toString(), file.getFileName().toString().replaceAll("\\.[a-zA-Z].+", ""));
                } else {
                    rejectedFiles.add(file.getFileName().toString());
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public boolean doFilter() {
        if (!rejectedFiles.isEmpty()) {
            printOverSizeFileNameInfo();
            rejectedFiles = new ArrayList<>();
            return false;
        }
        return !isEmptyDirs();
    }

    private void printOverSizeFileNameInfo() {
        for (String str : rejectedFiles) {
            System.out.println("--> " + str.substring(0, 50) +
                    "| - Имя заканчивается на 50-ом символе. Длина имени должна быть не больше 50 символов, включая расширение (.pdf, .doc, и т.д)");
        }
    }
}
