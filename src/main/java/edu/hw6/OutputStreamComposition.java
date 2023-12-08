package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public final class OutputStreamComposition {
    public static void writeText(String text, Path path) throws IOException {
        if (text == null || path == null || !path.toFile().isFile()) {
            throw new NullPointerException("Текст и путь не должны быть null!");
        }
        try (var outputStream = Files.newOutputStream(path)) {
            try (var checked = new CheckedOutputStream(outputStream, new Adler32())) {
                bufferThenEncodeThenPrint(text, checked);
            }
        }
    }

    private static void bufferThenEncodeThenPrint(String text, CheckedOutputStream checked) throws IOException {
        try (var buffered = new BufferedOutputStream(checked)) {
            try (var writer = new OutputStreamWriter(buffered, StandardCharsets.UTF_8)) {
                try (var print = new PrintWriter(writer)) {
                    print.print(text);
                }
            }
        }
    }

    private OutputStreamComposition() {
    }
}
