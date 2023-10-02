package hangman;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DictionaryTest {
    @Test
    @DisplayName("Тест выбора рандомного слова из словаря")
    void chooseRandomWordTest1() {
        // given

        // when
        String s = Dictionary.chooseRandomWord();

        // then
        assertThat(Dictionary.DICT).contains(s);
    }

    @Test
    @DisplayName("Тест выбора слова, которое не лежит в словаре")
    void chooseRandomWordTest2() {
        // given

        // when
        String s = "точно не в словаре";

        // then
        assertThat(Dictionary.DICT).doesNotContain(s);
    }
}
