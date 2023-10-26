package hangman;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static hangman.Hangman.GameState.LOST;
import static hangman.Hangman.GameState.RUNNING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HangmanTest {
    @Test
    @DisplayName("Проверка создания объекта Hangman")
    void hangmanTest1() {
        // given
        final int wrongAttemptsAllowed = 5;
        final Hangman hangman = new Hangman(wrongAttemptsAllowed);

        // when

        // then
        assertThat(hangman.wrongAttemptsAllowed)
            .isEqualTo(wrongAttemptsAllowed);
        assertThat(hangman.gameState)
            .isEqualTo(RUNNING);
        assertThat(hangman.gameSession.wrongAttemptsMade)
            .isEqualTo(0);
    }

    @Test()
    @DisplayName("Тест на некорректное создание объекта Hangman")
    void hangmanTest2() {
        try {
            // given
            final int wrongAttemptsAllowed = -1;
            final Hangman hangman = new Hangman(wrongAttemptsAllowed);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage("wrongAttemptsAllowed should be > 0");
        }
    }

    @Test()
    @DisplayName("Тест на ввод корректного символа от пользователя")
    void hangmanTest3() {
        // given
        final int wrongAttemptsAllowed = 1;
        final Hangman hangman = new Hangman(wrongAttemptsAllowed);

        // Первая буква входит в слово, вторая -- произвольная, нужна для того,
        // Чтобы закончить выполнение run()
        char userInput1 = hangman.gameSession.answer.charAt(0);
        char userInput2 = 'ὠ';
        String input = String.valueOf(new char[] {userInput1, '\n', userInput2});
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // when
        hangman.run();

        // then
        assertThat(hangman.gameState)
            .isEqualTo(LOST);
        assertThat(hangman.gameSession.gameStatus)
            .contains(userInput1);
        assertThat(hangman.gameSession.gameStatus)
            .doesNotContain(userInput2);
    }
}
