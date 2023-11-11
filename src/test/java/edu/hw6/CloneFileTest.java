package edu.hw6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static edu.hw6.CloneFile.cloneFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CloneFileTest {
    @Test
    @DisplayName("Тест с одним файлом")
    void cloneFileTest1() throws IOException {
        Path path = Paths.get("src", "res", "test.txt");
        Path copiedPath = cloneFile(path);
        assertThat(copiedPath).isEqualTo(Paths.get("src", "res", "test - копия.txt"));

        Files.delete(copiedPath);
    }

    @Test
    @DisplayName("Тест с двумя файлами")
    void cloneFileTest2() throws IOException {
        Path path = Paths.get("src", "res", "test.txt");
        Path copiedPath = cloneFile(path);
        assertThat(copiedPath).isEqualTo(Paths.get("src", "res", "test - копия.txt"));
        Path newCopiedPath = cloneFile(copiedPath);
        assertThat(newCopiedPath).isEqualTo(Paths.get("src", "res", "test - копия - копия.txt"));
        Path oneMoreCopiedPath = cloneFile(path);
        assertThat(oneMoreCopiedPath).isEqualTo(Paths.get("src", "res", "test - копия (2).txt"));

        Files.delete(copiedPath);
        Files.delete(newCopiedPath);
        Files.delete(oneMoreCopiedPath);
    }

    @Test
    @DisplayName("Тест с невалидным файлом")
    void cloneFileTest3() {
        Path path = Paths.get("src", "src", "test.txt");
        var thrown = Assertions.assertThrows(NullPointerException.class, () -> cloneFile(path));
        assertThat(thrown).hasMessage("Путь должен быть не null!");
    }
}
