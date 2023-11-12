package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.PathFilters.hasExtension;
import static edu.hw6.PathFilters.hasNameOfPattern;
import static edu.hw6.PathFilters.hasSizeBetween;
import static edu.hw6.PathFilters.magicNumber;
import static edu.hw6.PathFilters.readable;
import static edu.hw6.PathFilters.regularFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PathFiltersTest {
    @Test
    @DisplayName("Тест с текстовым файлом test.txt")
    void pathFiltersTest1() throws IOException {
        final long lower = 1;
        final long upper = 100_000;
        final byte[] bytes = {(byte) -16, (byte) -97, (byte) -104};
        DirectoryStream.Filter<Path> filter = regularFile
            .and(readable)
            .and(hasSizeBetween(lower, upper))
            .and(hasExtension("txt"))
            .and(hasNameOfPattern("t.*e.*t"))
            .and(magicNumber(bytes));

        List<Path> result = new ArrayList<>();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(Paths.get("src", "res"), filter)) {
            for (var entry : entries) {
                result.add(entry);
            }
        }

        assertThat(result).asList().contains(Paths.get("src", "res", "test.txt"));
    }
}
