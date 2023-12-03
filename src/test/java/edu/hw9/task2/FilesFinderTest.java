package edu.hw9.task2;

import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilesFinderTest {
    @Test
    @DisplayName("Тест нахождения больших папок")
    void findLargeDirsTest1() {
        Path root = Path.of(".");
        var dirs = FilesFinder.findLargeDirs(root);
        assertThat(dirs).asList().contains(Path.of(".", ".git"));
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
        assertThat(list).asList().contains(Path.of(".",".github", "workflows", "build.yml"));
    }

    @Test
    @DisplayName("Тест на поиск .txt с некорректным корнем")
    void findFilesByFilter2() {
        Path root = Path.of("README12312.md");
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> FilesFinder.findFilesByFilter(root, 0, ".yml"));
        assertThat(thrown).hasMessage("Данного пути не существует или это обычный файл!");
    }
}
