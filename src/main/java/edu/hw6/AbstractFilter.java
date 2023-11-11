package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    boolean accept(Path entry) throws IOException;

    default AbstractFilter and(AbstractFilter other) {
        return entry -> this.accept(entry) && other.accept(entry);
    }
}
