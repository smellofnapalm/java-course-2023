package edu.hw3.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static edu.hw3.task3.FreqDict.freqDict;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreqDictTest {
    @Test
    @DisplayName("Тест с классом String")
    void freqDictTest1() {
        var arr = new String[] {"a", "bb", "a", "bb"};

        var dict = freqDict(arr);

        assertThat(dict.get("a")).isEqualTo(2);
        assertThat(dict.get("bb")).isEqualTo(2);
        assertThat(dict.containsKey("aboba")).isFalse();
    }

    @Test
    @DisplayName("Тест с классом Integer")
    void freqDictTest2() {
        var arr = new Integer[] {2, 2, 2, 1, 3, 1, 4};

        var dict = freqDict(arr);

        assertThat(dict.get(2)).isEqualTo(3);
        assertThat(dict.get(1)).isEqualTo(2);
        assertThat(dict.containsKey(5)).isFalse();
    }

    @Test
    @DisplayName("Тест с null массивом")
    void freqDictTest3() {
        Integer[] arr = null;

        var dict = freqDict(arr);

        assertThat(dict).isEqualTo(new HashMap<Integer, Integer>());
    }
}
