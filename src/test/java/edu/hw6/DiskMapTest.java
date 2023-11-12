package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DiskMapTest {
    @Test
    @DisplayName("Проверка записи и чтения с диска")
    void diskMapTest1() throws IOException {
        var diskMap = new DiskMap();
        diskMap.put("1", "first");
        diskMap.put("2", "second");

        diskMap.save();
        diskMap.load();

        assertThat(diskMap.containsKey("1")).isTrue();
        assertThat(diskMap.containsKey("2")).isTrue();

        diskMap.delete();
    }

    @Test
    @DisplayName("Проверка записи и чтения с диска с эмодзи-символами")
    void diskMapTest2() throws IOException {
        var diskMap = new DiskMap();
        diskMap.put("\uD83E\uDD28", "first");
        diskMap.put("\uD83D\uDE28", "second");

        diskMap.save();
        diskMap.load();

        diskMap.delete();

        assertThat(diskMap.containsKey("\uD83E\uDD28")).isTrue();
        assertThat(diskMap.containsKey("\uD83D\uDE28")).isTrue();
    }

    @Test
    @DisplayName("Проверка чтения заранее созданного файла")
    void diskMapTest3() throws IOException {
        Path path = Paths.get("./src/res/test.txt");
        var diskMap = new DiskMap(path.toString());
        diskMap.load();

        assertThat(diskMap.containsKey("\uD83E\uDD28")).isTrue();
        assertThat(diskMap.containsKey("\uD83D\uDE28")).isTrue();
    }
}
