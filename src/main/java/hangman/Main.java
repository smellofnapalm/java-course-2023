package hangman;

public class Main {
    public static void main(String[] args) {
        final int wrongAttemptsAllowed = 5;
        Hangman game = new Hangman(wrongAttemptsAllowed);
        game.run();
    }

    private Main() {
    }
}
