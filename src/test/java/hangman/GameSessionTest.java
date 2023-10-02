package hangman;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameSessionTest {
    @Test
    @DisplayName("Проверка создания объекта GameSession")
    void gameSessionTest1() {
        // given
        final GameSession gameSession = new GameSession();
        int attemptsMade = gameSession.wrongAttemptsMade;
        String gameStatus = new String(gameSession.gameStatus);
        String answer = gameSession.answer;

        // when

        // then
        assertThat(attemptsMade).isEqualTo(0);
        assertThat(gameStatus).contains("*");
        assertThat(gameStatus).doesNotContain(answer);
    }

    @Test
    @DisplayName("Тест на букву, содержащуюся в answer")
    void gameSessionTest2() {
        // given
        final GameSession gameSession = new GameSession();
        char firstChar = gameSession.answer.charAt(0);

        // when
        boolean changed = gameSession.changeGameStatus(firstChar);
        int wrongAttemptsMade = gameSession.wrongAttemptsMade;
        String gameStatus = new String(gameSession.gameStatus);

        // then
        assertThat(wrongAttemptsMade).isEqualTo(0);
        assertThat(gameStatus).contains(String.valueOf(firstChar));
        assertThat(changed).isEqualTo(true);
    }

    @Test
    @DisplayName("Тест на букву, которая не содержится в answer")
    void gameSessionTest3() {
        // given
        final GameSession gameSession = new GameSession();
        char notInAnswerChar = 'ὠ';
        final String answer = gameSession.answer;

        // when
        boolean changed = gameSession.changeGameStatus(notInAnswerChar);
        int wrongAttemptsMade = gameSession.wrongAttemptsMade;
        String gameStatus = new String(gameSession.gameStatus);

        // then
        assertThat(wrongAttemptsMade).isEqualTo(1);
        assertThat(gameStatus).doesNotContain(String.valueOf(notInAnswerChar));
        assertThat(changed).isEqualTo(false);
        assertThat(answer).doesNotContain(String.valueOf(notInAnswerChar));
    }
}
