package edu.hw5.task6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw5.task6.IsSubsequence.isSubsequence;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IsSubsequenceTest {
    @Test
    @DisplayName("Тест из примера")
    void isSubsequenceTest1() {
        String sub = "abc";
        String text = "achfdbaabgabcaabg";

        assertThat(isSubsequence(sub, text)).isTrue();
    }

    @Test
    @DisplayName("Тест с пустой строкой (входит везде)")
    void isSubsequenceTest2() {
        String sub = "";
        String text = "achfdbaabgabcaabg";

        assertThat(isSubsequence(sub, text)).isTrue();
    }

    @Test
    @DisplayName("Тест с null")
    void isSubsequenceTest3() {
        String sub = null;
        String text = "achfdbaabgabcaabg";

        var thrown = Assertions.assertThrows(NullPointerException.class, () -> isSubsequence(sub, text));
        assertThat(thrown).hasMessage("Подстрока и текст не могут быть null!");
    }

    @Test
    @DisplayName("Тест с символами Unicode (если бы мы дробили по байтам, то этот тест бы не прошел)")
    void isSubsequenceTest4() {
        String sub = "\uD83E\uDD13a\uD83E\uDD2A";
        String text = "\uD83Eo\uDD13a\uD83Eb\uDD2A";
        assertThat(isSubsequence(sub, text)).isFalse();
    }

    @Test
    @DisplayName("Тест с символами Unicode")
    void isSubsequenceTest5() {
        String sub = "\uD83E\uDD13a\uD83E\uDD2A";
        String text = "asd\uD83E\uDD13dsasd\uD83E\uDD2Aasdas";
        assertThat(isSubsequence(sub, text)).isTrue();
    }
}
