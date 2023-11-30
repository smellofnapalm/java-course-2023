package edu.flame;

import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FlameMakerTest {
    @Test
    @DisplayName("Тест функции создания изображения")
    void flameMakerTest1() {
        var initializer = new Initializer(Path.of("config.txt"));
        var gen = new ImageGenerator(initializer.height, initializer.width);
        gen.generateImage(initializer.iterations, initializer.threshold, initializer.flameFunctions);
        assertThat(gen.res.length).isEqualTo(initializer.height);
        assertThat(gen.res[0].length).isEqualTo(initializer.width);
        assertThat(gen.res[0][0]).isNotNull();
    }

    @Test
    @DisplayName("Тест функции создания изображения, если файл записи в конфиге указан некорректно")
    void flameMakerTest2() {
        var initializer = new Initializer(Path.of("test_config.txt"));
        var thrown = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> {
                var gen = new ImageGenerator(initializer.height, initializer.width);
                gen.generateImage(initializer.iterations, initializer.threshold, initializer.flameFunctions);
            }
        );
        assertThat(thrown).hasMessage("-6000");
    }
}
