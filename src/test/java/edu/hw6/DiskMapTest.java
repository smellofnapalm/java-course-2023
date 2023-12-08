package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DiskMapTest {
    @Test
    @DisplayName("Проверка записи и чтения с диска")
    void diskMapTest1() throws IOException {
        Map<String, String> diskMap = new DiskMap("test1.txt");
        diskMap.put("1", "first");
        diskMap.put("2", "second");

        assertThat(diskMap.containsKey("1")).isTrue();
        assertThat(diskMap.containsKey("2")).isTrue();

        Files.delete(Path.of("test1.txt"));
    }

    @Test
    @DisplayName("Проверка записи и чтения с диска с эмодзи-символами")
    void diskMapTest2() throws IOException {
        Map<String, String> diskMap = new DiskMap("test2.txt");
        diskMap.put("\uD83E\uDD28", "first");
        diskMap.put("\uD83D\uDE28", "second");

        assertThat(diskMap.containsKey("\uD83E\uDD28")).isTrue();
        assertThat(diskMap.containsKey("\uD83D\uDE28")).isTrue();

        Files.delete(Path.of("test2.txt"));
    }

    @Test
    @DisplayName("Проверка чтения заранее созданного файла")
    void diskMapTest3() {
        Path path = Paths.get("./src/res/test.txt");
        Map<String, String> diskMap = new DiskMap(path.toString());
        diskMap.putAll(Map.of("1", "first", "2", "second"));

        assertThat(diskMap.containsKey("\uD83E\uDD28")).isTrue();
        assertThat(diskMap.containsKey("\uD83D\uDE28")).isTrue();
        assertThat(diskMap.containsKey("1")).isTrue();
        assertThat(diskMap.containsKey("2")).isTrue();

        diskMap.remove("1");
        assertThat(diskMap.containsKey("1")).isFalse();

        diskMap.remove("2");
        assertThat(diskMap.containsKey("2")).isFalse();
    }
}
