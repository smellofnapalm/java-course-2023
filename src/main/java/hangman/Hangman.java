package hangman;

import java.util.NoSuchElementException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hangman {
    enum GameState {
        RUNNING, LOST, WON
    }

    final int wrongAttemptsAllowed;
    GameSession gameSession;
    GameState gameState;

    public Hangman(int wrongAttemptsAllowed) throws IllegalArgumentException {
        this.wrongAttemptsAllowed = wrongAttemptsAllowed;
        if (wrongAttemptsAllowed <= 0) {
            throw new IllegalArgumentException("wrongAttemptsAllowed should be > 0");
        }
        this.gameSession = new GameSession();
        this.gameState = GameState.RUNNING;
    }

    private void changeGameState() {
        String currentWord = new String(gameSession.gameStatus);
        if (gameSession.answer.equals(currentWord)) {
            gameState = GameState.WON;
        } else if (gameSession.wrongAttemptsMade >= wrongAttemptsAllowed) {
            gameState = GameState.LOST;
        }
    }

    private void runGame() {
        final Scanner in = new Scanner(System.in);
        final Logger LOGGER = LogManager.getLogger();
        while (gameState.equals(GameState.RUNNING)) {
            LOGGER.info("Guess a letter:");
            String input = in.nextLine();
            if (input.length() != 1 || !Character.isAlphabetic(input.charAt(0))) {
                LOGGER.info("Your guess is incorrect!");
                continue;
            }
            boolean isHit = gameSession.changeGameStatus(input.charAt(0));
            if (isHit) {
                LOGGER.info("Hit!");
            } else {
                LOGGER.info(
                    "Missed, mistake {} out of {}\n",
                    gameSession.wrongAttemptsMade, wrongAttemptsAllowed
                );
            }
            changeGameState();
            LOGGER.info("The word: {}\n", String.valueOf(gameSession.gameStatus));
        }
        if (gameState.equals(GameState.LOST)) {
            LOGGER.info("You lost!");
            LOGGER.info("The word was: {}\n", gameSession.answer);
        } else {
            LOGGER.info("You won!");
        }

    }

    public void run() {
        final Logger LOGGER = LogManager.getLogger();
        try {
            this.runGame();
        } catch (NoSuchElementException e) {
            LOGGER.info("Goodbye!");
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
