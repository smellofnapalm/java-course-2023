package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public abstract class PathFilters {
    public static AbstractFilter regularFile = Files::isRegularFile;
    public static AbstractFilter readable = Files::isReadable;

    public static AbstractFilter hasExtension(String extension) {
        return entry -> entry.getFileName().toString().matches(".*\\.%s".formatted(extension));
    }

    public static AbstractFilter hasSizeBetween(long lower, long upper) {
        return entry -> {
            try {
                return Files.size(entry) >= lower && Files.size(entry) <= upper;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static AbstractFilter hasNameOfPattern(String pattern) {
        return entry -> entry.getFileName().toString().split("\\.")[0].matches(pattern);
    }

    public static AbstractFilter magicNumber(byte... bytes) {
        return entry -> {
            int length = bytes.length;
            try (var reader = Files.newInputStream(entry)) {
                var data = new byte[length];
                reader.read(data);
                return Arrays.equals(data, bytes);
            }
        };
    }

    private PathFilters() {
    }
}
