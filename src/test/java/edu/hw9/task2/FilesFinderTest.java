package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilesFinderTest {
    @Test
    @DisplayName("Тест нахождения больших папок")
    void findLargeDirsTest1() throws IOException {
        Path root = Path.of(".");
        final Path testDir = Path.of(".","testDir");
        Files.createDirectory(testDir);
        final Path insideTestDir = Path.of(String.valueOf(testDir), "insideTestDir");
        Files.createDirectory(insideTestDir);
        for (int i = 0; i < 995; i++) {
            Files.createFile(Path.of(String.valueOf(testDir), "%s.txt".formatted(i)));
        }
        for (int i = 0; i < 100; i++) {
            Files.createFile(Path.of(String.valueOf(insideTestDir), "%s.txt".formatted(i)));
        }
        var dirs = FilesFinder.findLargeDirs(root);
        for (int i = 0; i < 995; i++) {
            Files.delete(Path.of(String.valueOf(testDir), "%s.txt".formatted(i)));
        }
        for (int i = 0; i < 100; i++) {
            Files.delete(Path.of(String.valueOf(insideTestDir), "%s.txt".formatted(i)));
        }
        Files.delete(insideTestDir);
        Files.delete(testDir);
        assertThat(dirs).asList().contains(testDir);
    }

    @Test
    @DisplayName("Тест с некорректным корнем")
    void findLargeDirsTest2() {
        Path root = Path.of("README.md");
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> FilesFinder.findLargeDirs(root));
        assertThat(thrown).hasMessage("Данного пути не существует или это обычный файл!");
    }

    @Test
    @DisplayName("Тест на поиск .yml")
    void findFilesByFilter1() {
        Path root = Path.of(".");
        var list = FilesFinder.findFilesByFilter(root, 0, ".yml");
        assertThat(list).asList().contains(Path.of(".", ".github", "workflows", "build.yml"));
    }

    @Test
    @DisplayName("Тест на поиск .txt с некорректным корнем")
    void findFilesByFilter2() {
        Path root = Path.of("README12312.md");
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> FilesFinder.findFilesByFilter(root, 0, ".yml")
        );
        assertThat(thrown).hasMessage("Данного пути не существует или это обычный файл!");
    }
}
