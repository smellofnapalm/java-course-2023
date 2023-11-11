package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CloneFile {
    static public Path cloneFile(Path path) throws IOException {
        if (path == null || !path.toFile().isFile()) {
            throw new NullPointerException("Путь должен быть не null!");
        }
        var fileNameAndExtension = path.getFileName().toString().split("\\.");
        String fileName = fileNameAndExtension[0];
        String extension = fileNameAndExtension[1];
        String pattern = "^%s - копия( \\([0-9]+\\))?.%s$".formatted(fileName, extension);
        int countCopies = path.getParent().toFile().listFiles((dir, name) -> name.matches(pattern)).length + 1;

        String newFileName;
        if (countCopies == 1) {
            newFileName = "%s - копия.%s".formatted(fileName, extension);
        } else {
            newFileName = "%s - копия (%d).%s".formatted(fileName, countCopies, extension);
        }

        Path copiedPath = Paths.get(path.getParent().toString(), newFileName);
        Files.createFile(copiedPath);
        return copiedPath;
    }

    private CloneFile() {
    }
}
