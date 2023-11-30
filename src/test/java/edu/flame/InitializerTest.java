package edu.flame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InitializerTest {
    @Test
    @DisplayName("Тестирование считывания конфига")
    void initializerTest1() {
        final Path configPath = Path.of("config.txt");
        var init = new Initializer(configPath);
        assertThat(init.format).isEqualTo("bmp");
    }

    @Test
    @DisplayName("Тестирование считывания из неправильного файла")
    void initializerTest2() {
        final Path configPath = Path.of("badConfig.txt");
        var thrown = Assertions.assertThrows(IllegalArgumentException.class, () ->new Initializer(configPath));
        assertThat(thrown).hasMessage("Путь к конфигу некорректный!");
    }
}
