package edu.hw10.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FibCalculatorTest {

    static {
        try {
            Files.createDirectory(Path.of("temp"));
        } catch (IOException ignored) {
        }
    }

    @Test
    @DisplayName("Проверка записи чисел Фибоначчи на диск")
    void fibCalcTest1() throws IOException {
        final int number = 10;
        FibCalculator proxy = CacheProxy.create(new FibCalcImpl(), FibCalculator.class);
        proxy.fib(number);
        Path path = Path.of("temp", 5902276 + ".txt");
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.readString(path)).isEqualTo("55");
    }
}
