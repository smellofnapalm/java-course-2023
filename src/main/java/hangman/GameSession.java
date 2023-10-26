package hangman;

final class GameSession {
    final String answer;
    int wrongAttemptsMade;
    char[] gameStatus;

    GameSession() {
        this.answer = Dictionary.chooseRandomWord();
        this.wrongAttemptsMade = 0;
        this.gameStatus = createGameStatus(answer);
    }

    private char[] createGameStatus(String answer) {
        char[] status = new char[answer.length()];
        for (int i = 0; i < answer.length(); i++) {
            status[i] = '*';
        }
        return status;
    }

    boolean changeGameStatus(char userInput) {
        boolean flag = false;
        for (int i = 0; i < answer.length(); i++) {
            if (userInput == answer.charAt(i)) {
                gameStatus[i] = userInput;
                flag = true;
            }
        }
        if (!flag) {
            wrongAttemptsMade++;
        }
        return flag;
    }
}
