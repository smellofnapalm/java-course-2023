package edu.hw6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static edu.hw6.OutputStreamComposition.writeText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OutputStreamCompositionTest {
    @Test
    @DisplayName("Тест с текстом из примера")
    void compositionTest1() throws IOException {
        String text = "Programming is learned by writing programs. ― Brian Kernighan";
        Path path = Paths.get("src", "res", "compositionTest.txt");
        writeText(text, path);
        var result = Files.readString(path);
        assertThat(result).isEqualTo(text);
    }

    @Test
    @DisplayName("Тест с некорректным Path")
    void compositionTest2() {
        String text = "Programming is learned by writing programs. ― Brian Kernighan";
        Path path = Paths.get("src", "src", "compositionTest.txt");
        var thrown = Assertions.assertThrows(NullPointerException.class, () -> writeText(text, path));
        assertThat(thrown).hasMessage("Текст и путь не должны быть null!");
    }
}
